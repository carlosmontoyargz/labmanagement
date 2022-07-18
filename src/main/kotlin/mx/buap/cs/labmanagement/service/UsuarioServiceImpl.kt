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
package mx.buap.cs.labmanagement.service

import mx.buap.cs.labmanagement.repository.UsuarioRepository
import mx.buap.cs.labmanagement.error.SignUpException
import mx.buap.cs.labmanagement.model.Usuario
import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

/**
 *
 * @author Carlos Montoya
 * @since 1.0
 */
@Service
class UsuarioServiceImpl
    @Autowired constructor(
        val usuarioRepository: UsuarioRepository
        //val passEncoder: PasswordEncoder
        )
    : UsuarioService
{
    /**
     * Verifica que el usuario no este registrado, y codifica la
     * contraseña para ser guardada encriptada en la base de datos.
     *
     * @param usuario El usuario a intentar registrar en el sistema
     */
    @Throws(SignUpException::class)
    override fun preregistrar(usuario: Usuario): Usuario {
        if (usuarioRepository.existsByCorreo(usuario.correo!!))
            throw SignUpException(
                "El correo ya se encuentra registrado en el sistema.")

        if (usuarioRepository.existsByMatricula(usuario.matricula!!))
            throw SignUpException(
                "La matrícula ya se encuentra registrada en el sistema.")

        return encodePassword(usuario);
    }

    override fun encodePassword(usuario: Usuario) =
        usuario
            //.apply { password = passEncoder.encode(password!!) }
}
