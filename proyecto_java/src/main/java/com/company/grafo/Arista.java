package com.company.grafo;

import com.company.enums.TipoRelacion;

public class Arista {
    private Vertice destino;
    private TipoRelacion tipoRelacion;

    public Arista(Vertice destino, TipoRelacion tipoRelacion) {
        this.destino = destino;
        this.tipoRelacion = tipoRelacion;
    }

    public Vertice getDestino() {
        return destino;
    }

    public TipoRelacion getTipoRelacion() {
        return tipoRelacion;
    }
}
