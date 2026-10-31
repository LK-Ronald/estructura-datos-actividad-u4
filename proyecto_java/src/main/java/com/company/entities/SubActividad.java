package com.company.entities;

import com.company.grafo.ElementoGrafo;

import java.time.LocalTime;

public class SubActividad implements ElementoGrafo {
    private String id;
    private String nombre;
    private String descripcion;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public SubActividad(String id, String nombre,
                        String descripcion,
                        LocalTime horaInicio,
                        LocalTime horaFin) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    @Override
    public String toString() {
        return "Subactividad: " + nombre;
    }
}
