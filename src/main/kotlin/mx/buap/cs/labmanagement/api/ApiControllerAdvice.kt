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

import mx.buap.cs.labmanagement.documentos.exception.DocumentoNoEncontradoException
import mx.buap.cs.labmanagement.error.SignUpException
import mx.buap.cs.labmanagement.error.UsuarioNoEncontradoException
import mx.buap.cs.labmanagement.api.dto.ErrorResponse
import org.springframework.http.HttpStatus
//import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.persistence.PersistenceException

/**
 *
 * @author Carlos Montoya
 * @since 1.0
 */
@ControllerAdvice
class ApiControllerAdvice
{
//    @ResponseBody
//    @ExceptionHandler(AuthenticationException::class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    fun authenticationFailedHandler(ex: AuthenticationException) =
//        defaultResponse(ex)

    @ResponseBody
    @ExceptionHandler(SignUpException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun signUpHandler(ex: SignUpException) = defaultResponse(ex)

    @ResponseBody
    @ExceptionHandler(PersistenceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun persistanceExceptionHandler(ex: Exception) = defaultResponse(ex)

    @ResponseBody
    @ExceptionHandler(
        UsuarioNoEncontradoException::class,
        DocumentoNoEncontradoException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun entityNotFoundHandler(ex: Exception) = defaultResponse(ex)

    private fun defaultResponse(ex: Exception) =
        ErrorResponse(
            mensaje = ex.message,
            tipo = ex.javaClass.simpleName)
}
