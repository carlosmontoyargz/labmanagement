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
package mx.buap.cs.labmanagement.api

import mx.buap.cs.labmanagement.error.EquipoNoEncontradoException
import mx.buap.cs.labmanagement.error.UsuarioNoEncontradoException
import mx.buap.cs.labmanagement.model.Documento
import mx.buap.cs.labmanagement.model.Imagen
import mx.buap.cs.labmanagement.service.DocumentoService
import mx.buap.cs.labmanagement.service.ImagenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.util.*

/**
 * @author Carlos Montoya
 * @since 1.0
 */
@RepositoryRestController
class ArchivoUploadController
    @Autowired constructor(
        val colaboradorRepository: ColaboradorRestRepository,
        val equipoRepository: EquipoRestRepository,
        val documentoService: DocumentoService,
        val imagenService: ImagenService)
{
    @PostMapping("/colaboradores/{colaboradorId}/documentos")
    fun subirDocumento(@PathVariable colaboradorId: Int,
                       @RequestParam multipartFile: MultipartFile): ResponseEntity<String> {
        val colaboradorDb = colaboradorRepository
            .findById(colaboradorId)
            .orElseThrow { UsuarioNoEncontradoException(colaboradorId) }

        //TODO sustituir documento si ya existe por nombre

        val documento = documentoService.guardar(
            documento = Documento().apply {
                nombre = multipartFile.originalFilename
                colaborador = colaboradorDb
            },
            bytes = multipartFile.bytes)

        return ResponseEntity
            .created(URI.create("/documentos/${documento.id}")) //TODO url del recurso creado
            .build()
    }

    @PutMapping("/equipos/{equipoId}/imagen")
    fun subirImagenEquipo(@PathVariable equipoId: Int,
                          @RequestParam multipartFile: MultipartFile): ResponseEntity<String> {
        val equipo = equipoRepository
            .findById(equipoId)
            .orElseThrow { EquipoNoEncontradoException(equipoId) }

        equipo.imagen = imagenService.guardar(
            imagen = Imagen().apply {
                nombre = multipartFile.originalFilename
            },
            bytes = multipartFile.bytes)
        equipoRepository.save(equipo)

        return ResponseEntity
            .created(URI.create("/imagenes/${equipo.imagen.id}"))
            .build()
    }
}
