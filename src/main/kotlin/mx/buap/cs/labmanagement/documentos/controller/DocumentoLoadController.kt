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
package mx.buap.cs.labmanagement.documentos.controller

import mx.buap.cs.labmanagement.documentos.model.Documento
import mx.buap.cs.labmanagement.documentos.model.DocumentoLob
import mx.buap.cs.labmanagement.documentos.service.DocumentoLobService
import mx.buap.cs.labmanagement.error.UsuarioNoEncontradoException
import mx.buap.cs.labmanagement.model.Colaborador
import mx.buap.cs.labmanagement.usuarios.ColaboradorRepository
import mx.buap.cs.labmanagement.service.ImagenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.util.Optional
import java.util.UUID
import javax.transaction.Transactional

/**
 * @author Carlos Montoya
 * @since 1.0
 */
@RestController
@RequestMapping("/documento-load")
class DocumentoLoadController
    @Autowired constructor(
        val documentoLobService: DocumentoLobService,
        val colaboradorRepository: ColaboradorRepository)
{
    @PostMapping("/")
    fun subirDocumento(
            @RequestParam colaboradorId: Long?,
            @RequestParam multipartFile: MultipartFile)
    : ResponseEntity<String> {
        val documento: DocumentoLob = documentoLobService
            .guardar(Documento().apply {
                this.nombre      = multipartFile.originalFilename
                this.colaborador =
                    if (colaboradorId == null) null
                    else colaboradorRepository
                        .findById(colaboradorId)
                        .orElseThrow { UsuarioNoEncontradoException(colaboradorId) }
            },
            multipartFile.bytes)

        return ResponseEntity
            .created(URI.create("/documento-load/${documento.id}"))
            .build()
    }

    @GetMapping("/{documentoId}"/*, produces = [MediaType.ALL_VALUE]*/)
    @Transactional
    fun descargarDocumento(@PathVariable documentoId: Long): ResponseEntity<Resource> {
        val documentoLob: DocumentoLob = documentoLobService.encontrar(documentoId)
        return ResponseEntity
            .ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"${documentoLob.documento.nombre}\"")
            .body(ByteArrayResource(documentoLob.contenido))
    }
}
