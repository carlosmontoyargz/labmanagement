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

package mx.buap.cs.labmanagement.documentos.service

import mx.buap.cs.labmanagement.documentos.model.Documento
import mx.buap.cs.labmanagement.documentos.model.DocumentoLob
import mx.buap.cs.labmanagement.documentos.repository.DocumentoLobRepository
import mx.buap.cs.labmanagement.documentos.repository.DocumentoRepository
import mx.buap.cs.labmanagement.documentos.exception.DocumentoNoEncontradoException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DocumentoLobServiceImpl
    @Autowired constructor(
        val documentoRepository: DocumentoRepository,
        val documentoLobRepository: DocumentoLobRepository)
    : DocumentoLobService
{
    @Transactional
    override fun guardar(pDocumento: Documento, bytes: ByteArray): DocumentoLob =
        documentoLobRepository.save(
            DocumentoLob().apply {
                contenido = bytes
                documento = documentoRepository
                    .findByColaboradorAndNombre(pDocumento.colaborador, pDocumento.nombre)
                    .map       { it }
                    .orElseGet { pDocumento }
            })

    @Transactional
    @Throws(DocumentoNoEncontradoException::class)
    override fun encontrar(documentoId: Long): DocumentoLob =
        documentoLobRepository
            .findById(documentoId)
            .orElseThrow { DocumentoNoEncontradoException(documentoId) }
}
