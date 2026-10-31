package com.company.grafo;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private ElementoGrafo entidad;

    private List<Arista> conexiones;

    public Vertice(ElementoGrafo entidad) {
        this.entidad = entidad;
        this.conexiones = new ArrayList<>();
    }

    public ElementoGrafo getEntidad() {
        return entidad;
    }

    public List<Arista> getConexiones() {
        return conexiones;
    }

    public void agregarConexion(Arista arista) {
        conexiones.add(arista);
    }

    @Override
    public String toString() {
        return entidad.getId() + " - "
                + entidad.getNombre();
    }
}
