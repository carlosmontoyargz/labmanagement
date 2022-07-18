/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021. Carlos Alberto Montoya Rodríguez.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package mx.buap.cs.labmanagement.config

import mx.buap.cs.labmanagement.api.*
import mx.buap.cs.labmanagement.model.*
import mx.buap.cs.labmanagement.repository.UsuarioRepository
import mx.buap.cs.labmanagement.service.DocumentoService
import mx.buap.cs.labmanagement.service.UsuarioService
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Configuration
class InitialDataConfig
    @Autowired constructor(
        val usuarioService: UsuarioService,
        val usuarioRepository: UsuarioRepository,
        val documentoService: DocumentoService,
        val laboratorioRepository: LaboratorioRestRepository,
        val equipoRepository: EquipoRestRepository,
        val materiaRepository: MateriaRestRepository,
        val bitacoraRestRepository: BitacoraRestRepository,
        val incidenteRepository: IncidenteRestRepository,
        val solicitudesRepository: SolicitudesRestRepository,
        val mensajeRepository: MensajeRepository)
{
    private val log = LogManager.getLogger(InitialDataConfig::class.java)

    @Bean
    fun cargaInicialDatos() = CommandLineRunner {
        log.info("Comienza carga de datos de prueba")
        val laboratorio = guardarlaboratorio()
        val colaboradores = guardarColaboradores()
        val profesor = guardarProfesor()
        val materias = guardarMaterias()
        guardarDocumentos(colaboradores)
        guardarEquipos(laboratorio)
        guardarEntradas(toList(materias), colaboradores, profesor)
        guardarSolicitudes()
        guardarMensajes(profesor)
        log.info("Finaliza carga de datos de prueba")
    }

    private fun guardarlaboratorio() =
        laboratorioRepository.save(Laboratorio().apply {
            nombre = "Laboratorio de Bases de Datos de la FCC"
            edificio = "CC02"
            salon = "003"
        })

    private fun guardarMaterias() = materiaRepository.saveAll(listOf(
        materiaFrom("ICC002", "Bases de Datos"),
        materiaFrom("ICC003", "Mineria de Datos"),
        materiaFrom("ICC004", "Procesamiento de la Informacion")
    ))

    private fun materiaFrom(clave1: String, nombre1: String) =
        Materia().apply { clave = clave1; nombre = nombre1 }

    private fun guardarProfesor() = usuarioRepository.save(
        usuarioService.preregistrar(Profesor().apply {
            nombre = "Carlos"
            apellidoPaterno = "Montoya"
            apellidoMaterno = "Rodriguez"
            matricula = "201325916"
            correo = "carlos.montoya@profesor.buap.mx"
            password = "admin"
            creado = LocalDateTime.now()
            telefono = "2125295121"
            isActivo = true
            isResponsable = true
        })
    ) as Profesor

    private fun guardarColaboradores() = usuarioRepository.saveAll(listOf(
        preregistrar(Colaborador().apply {
            nombre = "Juan"
            apellidoPaterno = "Lopez"
            apellidoMaterno = "Gomez"
            matricula = "201456145"
            correo = "juan.lopez@alumno.buap.mx"
            password = "juan.lopez"
            telefono = "3324697114"
            isActivo = true
            isResponsable = true
            carrera = "ICC"
            inicioServicio = LocalDate.now().minusMonths(4)
            conclusionServicio = LocalDate.now()
        }),
        preregistrar(Colaborador().apply {
            nombre = "Diego"
            apellidoPaterno = "Ruiz"
            apellidoMaterno = "Gonzalez"
            matricula = "201566341"
            correo = "diego.ruiz@alumno.buap.mx"
            password = "diego.ruiz"
            telefono = "5524697114"
            isActivo = true
            isResponsable = false
            carrera = "ITI"
            inicioServicio = LocalDate.now().minusMonths(4)
            conclusionServicio = null
        }),
    ))

    private fun preregistrar(usuario: Usuario) = usuarioService.preregistrar(usuario)

    private fun guardarDocumentos(colaboradores: List<Usuario>) {
        for (i in 1..10) {
            documentoService.guardar(
                Documento().apply {
                    nombre = "documento_$i.docx" //path.fileName.toString()
                    fechaCreacion = LocalDateTime.now().minusDays(i.toLong())
                    this.colaborador = colaboradores[i % colaboradores.size] as Colaborador?
                },
                Files.readAllBytes(Paths.get("api/files/documento.docx")))
        }
    }

    private fun guardarEquipos(laboratorio: Laboratorio) {
        val marcas = arrayOf("Lenovo", "Samsung", "Apple")
        for (i in 1..50) {
            equipoRepository.save(Equipo().apply {
                serial = "${i * 100 + 12345}CC-VRR"
                marca = marcas[i % 3]
                descripcion = "Equipo $i"
                numeroInventario = i
                this.laboratorio = laboratorio
            })
        }
    }

    private fun guardarEntradas(materias: List<Materia>,
                                colaboradores: List<Usuario>,
                                profesorP: Profesor) {
        for (i in 1..60) {
            val entradaBitacora = bitacoraRestRepository.save(
                EntradaBitacora().apply {
                    fecha = LocalDate.now().minusDays(i.toLong())
                    horaEntrada = LocalTime.of(i % 8 + 7, 0)
                    horaSalida = horaEntrada.plusHours(2)
                    colaborador = colaboradores[i % colaboradores.size] as Colaborador
                    if (i % 5 != 1) {
                        materia = materias[i % materias.size]
                        profesor = profesorP
                    }
                }
            )
            val numInicidentes = i % 4
            if (numInicidentes == 0) continue
            for (j in 0..numInicidentes) {
                incidenteRepository.save(
                    Incidente().apply {
                        id = IncidenteId().apply {
                            entradaId = entradaBitacora.id
                            numeroIncidente = j
                        }
                        descripcion = "Incidente de prueba #$j"
                    })
            }
        }
    }

    private fun guardarSolicitudes() {
        for (i in 1..60) {
            solicitudesRepository.save(Solicitud().apply {
                folio = "00000$i"
                descripcion = "Solicitud de prueba #$i"
                costo = BigDecimal.valueOf(i.toLong()) * BigDecimal.valueOf(10.01)
                estado = EstadoSolicitud.values()[i % 5]
                tipo = TipoSolicitud.values()[i % 5]
            })
        }
    }

    private fun guardarMensajes(usuario: Usuario) {
        for (i in 1..60) {
            mensajeRepository.save(Mensaje().apply {
                titulo = "Mensaje de prueba #$i"
                contenido = "Este es un mensaje de prueba."
                enviado = LocalDateTime.now().minusDays(i.toLong())
                enviadoPor = usuario
            })
        }
    }

    fun <T: Any> toList(itr: Iterable<T>): List<T> =
        arrayListOf<T>().apply { itr.forEach(this::add) }
}
