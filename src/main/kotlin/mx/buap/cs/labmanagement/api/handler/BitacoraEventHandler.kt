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

package mx.buap.cs.labmanagement.api.handler

import mx.buap.cs.labmanagement.api.BitacoraRestRepository
import mx.buap.cs.labmanagement.api.ColaboradorRestRepository
import mx.buap.cs.labmanagement.model.EntradaBitacora
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.HandleBeforeSave
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Component
@RepositoryEventHandler(EntradaBitacora::class)
class BitacoraEventHandler
    @Autowired constructor(
        val colaboradorRepository: ColaboradorRestRepository,
        val bitacoraRepository: BitacoraRestRepository)
{
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    private val log = LogManager.getLogger(BitacoraEventHandler::class.java)

    @HandleBeforeCreate
    fun beforeCreate(entrada: EntradaBitacora) {
        entrada.colaborador.tiempoPrestado.incrementar(entrada.duracion)
        colaboradorRepository.save(entrada.colaborador)
    }

    @HandleBeforeSave
    @Transactional
    fun beforeSave(entrada: EntradaBitacora) {
        entityManager.detach(entrada)
        bitacoraRepository
            .findById(entrada.id)
            .ifPresent { existente ->
                corregirTiempoColaborador(existente, entrada)
            }
    }

    private fun corregirTiempoColaborador(
        existente: EntradaBitacora, nueva: EntradaBitacora)
    {
        log.trace("Inicia correccion de tiempo prestado para colaborador {}", existente.colaborador.id)

        val tiempoPrestado = existente.colaborador.tiempoPrestado
        log.trace("Tiempo anterior: {}", tiempoPrestado)

        tiempoPrestado.reducir(existente.duracion)
        log.trace("Tiempo reducido: {}", tiempoPrestado)

        tiempoPrestado.incrementar(nueva.duracion)
        log.trace("Tiempo actualizado: {}", tiempoPrestado)

        colaboradorRepository.save(existente.colaborador)
        log.trace("Tiempo de colaborador actualizado")
    }
}
