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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Carlos Montoya
 * @since 1.0
 */
@Embeddable
public class IncidenteId implements Serializable
{
    @Column(name = "entrada_id")
    private int entradaId;

    @Column(name = "numero_incidente")
    private int numeroIncidente;

    public IncidenteId() {
    }

    public IncidenteId(int entradaId, int numeroIncidente) {
        this.entradaId = entradaId;
        this.numeroIncidente = numeroIncidente;
    }

    public int getEntradaId() {
        return entradaId;
    }

    public void setEntradaId(int entradaId) {
        this.entradaId = entradaId;
    }

    public int getNumeroIncidente() {
        return numeroIncidente;
    }

    public void setNumeroIncidente(int numeroIncidente) {
        this.numeroIncidente = numeroIncidente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncidenteId)) return false;

        IncidenteId that = (IncidenteId) o;

        if (entradaId != that.entradaId) return false;
        return numeroIncidente == that.numeroIncidente;
    }

    @Override
    public int hashCode() {
        int result = entradaId;
        result = 31 * result + numeroIncidente;
        return result;
    }

    @Override
    public String toString() {
        return entradaId + "_" + numeroIncidente;
    }
}
