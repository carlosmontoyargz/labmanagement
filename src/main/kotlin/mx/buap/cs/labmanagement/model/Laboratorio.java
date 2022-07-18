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
import java.util.LinkedList;
import java.util.List;

/**
 * @author Carlos Montoya
 * @since 1.0
 */
@Entity
public class Laboratorio
{
    @Id
    @Column(name = "laboratorio_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "laboratorio_generator")
    @SequenceGenerator(name = "laboratorio_generator", sequenceName = "laboratorio_seq", allocationSize = 1)
    private int id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 20)
    private String edificio;

    @Column(length = 20)
    private String salon;

    @OneToMany(
            mappedBy = "laboratorio",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Equipo> equipos = new LinkedList<>();

    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
        equipo.setLaboratorio(this);
    }

    public void borrarEquipo(Equipo equipo) {
        equipos.remove(equipo);
        equipo.setLaboratorio(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Laboratorio)) return false;

        Laboratorio that = (Laboratorio) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
