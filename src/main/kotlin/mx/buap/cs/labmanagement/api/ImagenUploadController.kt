package mx.buap.cs.labmanagement.api

import mx.buap.cs.labmanagement.documentos.service.DocumentoLobService
import mx.buap.cs.labmanagement.error.EquipoNoEncontradoException
import mx.buap.cs.labmanagement.model.Imagen
import mx.buap.cs.labmanagement.service.ImagenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.util.UUID

/**
 * @author Carlos Montoya
 * @since 1.0
 */
@RepositoryRestController
class ImagenUploadController
    @Autowired constructor(
        val colaboradorRepository: ColaboradorRestRepository,
        val equipoRepository: EquipoRestRepository,
        val documentoLobService: DocumentoLobService,
        val imagenService: ImagenService
    )
{
    @PutMapping("/equipos/{equipoId}/imagen")
    fun subirImagenEquipo(@PathVariable equipoId: Int,
                          @RequestParam multipartFile: MultipartFile
    ): ResponseEntity<String> {
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

    @GetMapping("/imagenes/{imagenId}", produces = [MediaType.IMAGE_JPEG_VALUE])
    fun descargarImagen(@PathVariable imagenId: UUID): ResponseEntity<Resource> {
        val imagenLob = imagenService.encontrarLob(imagenId)
        return ResponseEntity
            .ok()
            .body(ByteArrayResource(imagenLob.contenido))
    }
}