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

import mx.buap.cs.labmanagement.documentos.model.Documento;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Carlos Montoya
 * @since 1.0
 */
@Entity
@PrimaryKeyJoinColumn(name = "colaborador_id")
public class Colaborador extends Usuario
{
    @Column(length = 60)
    private String carrera;

    private LocalDate inicioServicio;
    private LocalDate conclusionServicio;

    @OneToOne(
            mappedBy = "colaborador",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private TiempoPrestado tiempoPrestado;

    @Column(nullable = false)
    private boolean responsable = false;

    @OneToMany(
            mappedBy = "colaborador",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<Documento> documentos = new LinkedList<>();

    @PrePersist
    public void crearTiempoPrestado() {
        if (tiempoPrestado == null)
            setTiempoPrestado(new TiempoPrestado());
    }

    public void incrementarTiempoPrestado(Duration tiempo) {
        if (tiempoPrestado == null)
            setTiempoPrestado(new TiempoPrestado());
        tiempoPrestado.incrementar(tiempo);
    }

    public void reducirTiempoPrestado(Duration tiempo) {
        if (tiempoPrestado != null)
            tiempoPrestado.reducir(tiempo);
    }

    public void asignarTiempoPrestado(Duration tiempo) {
        if (tiempoPrestado == null)
            setTiempoPrestado(new TiempoPrestado());
        tiempoPrestado.setFromDuration(tiempo);
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void agregarDocumento(Documento documento) {
        documentos.add(documento);
        documento.setColaborador(this);
    }

    public void removerDocumento(Documento documento) {
        documentos.remove(documento);
        documento.setColaborador(null);
    }

    public void setTiempoPrestado(TiempoPrestado tiempo) {
        if (tiempo == null) {
            if (this.tiempoPrestado != null) {
                this.tiempoPrestado.setColaborador(null);
            }
        }
        else {
            tiempo.setColaborador(this);
        }
        this.tiempoPrestado = tiempo;
    }

    public TiempoPrestado getTiempoPrestado() {
        return tiempoPrestado;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public LocalDate getInicioServicio() {
        return inicioServicio;
    }

    public void setInicioServicio(LocalDate inicioServicio) {
        this.inicioServicio = inicioServicio;
    }

    public LocalDate getConclusionServicio() {
        return conclusionServicio;
    }

    public void setConclusionServicio(LocalDate conclusionServicio) {
        this.conclusionServicio = conclusionServicio;
    }

    public boolean isResponsable() {
        return responsable;
    }

    public void setResponsable(boolean responsable) {
        this.responsable = responsable;
    }
}
