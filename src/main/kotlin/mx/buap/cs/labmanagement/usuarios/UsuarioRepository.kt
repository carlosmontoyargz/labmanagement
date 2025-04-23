package mx.buap.cs.labmanagement.usuarios

import mx.buap.cs.labmanagement.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.Optional

/**
 *
 * @author Carlos Montoya
 * @since 1.0
 */
@RepositoryRestResource(exported = false)
interface UsuarioRepository : JpaRepository<Usuario, Int>
{
    fun findByCorreo(correo: String): Optional<Usuario>

    fun existsByCorreo(correo: String): Boolean

    fun existsByMatricula(matricula: String): Boolean
}