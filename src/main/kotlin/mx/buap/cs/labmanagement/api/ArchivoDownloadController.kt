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

import mx.buap.cs.labmanagement.service.DocumentoService
import mx.buap.cs.labmanagement.service.ImagenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * @author Carlos Montoya
 * @since 1.0
 */
@RestController
class ArchivoDownloadController
    @Autowired constructor(
        val documentoService: DocumentoService,
        val imagenService: ImagenService)
{
    @GetMapping("/archivos/{documentoId}"/*, produces = [MediaType.ALL_VALUE]*/)
    fun descargarDocumento(@PathVariable documentoId: Int): ResponseEntity<Resource> {
        val documentoLob = documentoService.encontrarLob(documentoId)
        return ResponseEntity
            .ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"${documentoLob.documento.nombre}\"")
            .body(ByteArrayResource(documentoLob.contenido))
    }

    @GetMapping("/imagenes/{imagenId}", produces = [MediaType.IMAGE_JPEG_VALUE])
    fun descargarImagen(@PathVariable imagenId: UUID): ResponseEntity<Resource> {
        val imagenLob = imagenService.encontrarLob(imagenId)
        return ResponseEntity
            .ok()
            .body(ByteArrayResource(imagenLob.contenido))
    }
}
