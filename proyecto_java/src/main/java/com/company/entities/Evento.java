package com.company.entities;

import com.company.grafo.ElementoGrafo;

import java.time.LocalDate;

public class Evento implements ElementoGrafo {
    private String id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Evento(String id, String nombre,
                  String descripcion,
                  LocalDate fechaInicio,
                  LocalDate fechaFin) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    @Override
    public String toString() {
        return "Evento: " + nombre;
    }
}
