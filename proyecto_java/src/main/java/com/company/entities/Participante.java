package com.company.entities;

import com.company.grafo.ElementoGrafo;

public class Participante implements ElementoGrafo {
    private String id;
    private String nombre;
    private String correo;
    private String institucion;

    public Participante(String id, String nombre,
                        String correo, String institucion) {

        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.institucion = institucion;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getInstitucion() {
        return institucion;
    }

    @Override
    public String toString() {
        return "Participante: " + nombre;
    }
}
