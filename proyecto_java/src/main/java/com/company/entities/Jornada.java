package com.company.entities;

import com.company.grafo.ElementoGrafo;

import java.time.LocalDate;

public class Jornada implements ElementoGrafo {
    private String id;
    private String nombre;
    private LocalDate fecha;

    public Jornada(String id, String nombre, LocalDate fecha) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Jornada: " + nombre;
    }
}
