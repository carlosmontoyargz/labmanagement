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

import javax.persistence.*;
import java.time.Duration;

/**
 * @author Carlos Montoya
 * @since 1.0
 */
@Entity
public class TiempoPrestado
{
    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "colaborador_id")
    @MapsId
    private Colaborador colaborador;

    private long horas = 0;
    private int minutos = 0;

    public void incrementar(Duration tiempo) {
        setFromDuration(toDuration().plus(tiempo));
    }

    public void reducir(Duration tiempo) {
        setFromDuration(toDuration().minus(tiempo));
    }

    public void setFromDuration(Duration d) {
        horas = d.toHours();
        minutos = d.toMinutesPart();
    }

    public Duration toDuration() {
        return Duration
                .ofHours(horas)
                .plusMinutes(minutos);
    }

    public String toString() {
        return horas + " H " + minutos + "M";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public long getHoras() {
        return horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setHoras(long horas) {
        this.horas = horas;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
}
