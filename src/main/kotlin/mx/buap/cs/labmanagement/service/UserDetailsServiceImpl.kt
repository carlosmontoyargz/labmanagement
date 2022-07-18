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
//
//import mx.buap.cs.labmanagement.model.Colaborador
//import mx.buap.cs.labmanagement.model.Profesor
//import mx.buap.cs.labmanagement.model.Usuario
//import mx.buap.cs.labmanagement.repository.UsuarioRepository
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Primary
//import org.springframework.security.core.authority.SimpleGrantedAuthority
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//import org.springframework.stereotype.Service
//import kotlin.jvm.Throws
//
///**
// *
// * @author Carlos Montoya
// * @since 1.0
// */
//@Primary
//@Service
//class UserDetailsServiceImpl
//    @Autowired constructor(val usuarioRepository: UsuarioRepository)
//    : UserDetailsService
//{
//    @Throws(UsernameNotFoundException::class)
//    override fun loadUserByUsername(username: String?): UserDetails =
//        usuarioRepository
//            .findByCorreo(username!!)
//            .map { usuario ->
//                User(username, usuario.password!!, usuario.isActivo,
//                    true, true, true,
//                    createAuthorities(usuario))
//            }
//            .orElseThrow {
//                UsernameNotFoundException("Usuario $username no encontrado")
//            }
//
//    private fun createAuthorities(usuario: Usuario): List<SimpleGrantedAuthority> {
//        val authorities = mutableListOf<SimpleGrantedAuthority>()
//        when (usuario) {
//            is Colaborador -> {
//                authorities.add(SimpleGrantedAuthority("ROLE_COLABORADOR"))
//                if (usuario.isResponsable)
//                    authorities.add(SimpleGrantedAuthority("ROLE_RESPONSABLE"))
//            }
//            is Profesor -> {
//                authorities.add(SimpleGrantedAuthority("ROLE_PROFESOR"))
//                if (usuario.isResponsable)
//                    authorities.add(SimpleGrantedAuthority("ROLE_RESPONSABLE"))
//            }
//        }
//        return authorities
//    }
//}
