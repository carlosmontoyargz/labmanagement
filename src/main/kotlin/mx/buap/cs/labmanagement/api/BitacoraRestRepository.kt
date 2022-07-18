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

import mx.buap.cs.labmanagement.model.EntradaBitacora
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.web.bind.annotation.CrossOrigin

@RepositoryRestResource(path = "entradas", collectionResourceRel = "entradas")
interface BitacoraRestRepository: PagingAndSortingRepository<EntradaBitacora, Int>
{
    @Query("""
        SELECT e
        FROM
            EntradaBitacora e
            JOIN      e.colaborador c
            LEFT JOIN e.profesor p
            LEFT JOIN e.materia m
        WHERE
            lower(coalesce(m.nombre, '')) LIKE concat('%', lower(:q), '%')
            OR lower(concat(
                coalesce(p.nombre, ''),
                coalesce(p.apellidoPaterno, ''),
                coalesce(p.apellidoMaterno, ''))) LIKE concat('%', lower(:q), '%')
            OR lower(concat(
                coalesce(c.nombre, ''),
                coalesce(c.apellidoPaterno, ''),
                coalesce(c.apellidoMaterno, ''))) LIKE concat('%', lower(:q), '%')
    """)
    fun searchBy(@Param("q") q: String, pageable: Pageable): List<EntradaBitacora>
}
