package com.company.entities;

import com.company.grafo.ElementoGrafo;

public class Responsable implements ElementoGrafo {
    private String id;
    private String nombre;
    private String correo;
    private String telefono;

    public Responsable(String id, String nombre,
                       String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
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

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Responsable: " + nombre;
    }
}
