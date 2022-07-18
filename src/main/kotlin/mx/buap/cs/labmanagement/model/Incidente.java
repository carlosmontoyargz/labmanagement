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

package mx.buap.cs.labmanagement.model;

import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Carlos Montoya
 * @since 1.0
 */
@Entity
@RestResource
public class Incidente
{
    @EmbeddedId
    private IncidenteId id;

    @ManyToOne
    @JoinColumn(name = "entrada_id",insertable = false, updatable = false)
    private EntradaBitacora entrada;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @OneToMany(
            mappedBy = "incidente",
            cascade = CascadeType.ALL)
    private final List<Solicitud> solicitudes = new LinkedList<>();

    public void agregarSolicitud(Solicitud solicitud) {
        solicitudes.add(solicitud);
        solicitud.setIncidente(this);
    }

    public void removerSolicitud(Solicitud solicitud) {
        solicitudes.remove(solicitud);
        solicitud.setIncidente(null);
    }

    public IncidenteId getId() {
        return id;
    }

    public void setId(IncidenteId id) {
        this.id = id;
    }

    public EntradaBitacora getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaBitacora entrada) {
        this.id.setEntradaId(entrada.getId());
        this.entrada = entrada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Incidente)) return false;

        Incidente incidente = (Incidente) o;

        return Objects.equals(id, incidente.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
