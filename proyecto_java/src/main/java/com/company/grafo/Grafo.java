package com.company.grafo;

import com.company.entities.*;
import com.company.enums.TipoRelacion;

import java.util.*;

public class Grafo {
    private Map<String, Vertice> vertices;

    public Grafo() {
        vertices = new LinkedHashMap<>();
    }

    public void agregarElemento(ElementoGrafo entidad) {
        if (entidad == null || entidad.getId() == null
                || entidad.getId().isBlank()) {
            throw new IllegalArgumentException(
                    "La entidad o su identificador no son válidos"
            );
        }
        if (vertices.containsKey(entidad.getId())) {
            throw new IllegalArgumentException(
                    "Ya existe una entidad con ID: " + entidad.getId()
            );
        }

        Vertice nuevo = new Vertice(entidad);

        vertices.put(entidad.getId(), nuevo);
    }

    public Vertice buscarVertice(String id) {
        return vertices.get(id);
    }

    public <T extends ElementoGrafo> List<T> obtenerEntidadesPorTipo(
            Class<T> tipo) {
        List<T> entidades = new ArrayList<>();

        for (Vertice vertice : vertices.values()) {
            if (tipo.isInstance(vertice.getEntidad())) {
                entidades.add(tipo.cast(vertice.getEntidad()));
            }
        }

        return entidades;
    }

    public void mostrarEntidades() {
        System.out.println("\n========== ENTIDADES ==========");

        for (Vertice vertice : vertices.values()) {
            System.out.println(vertice);
        }
    }

    private boolean relacionValida(
            ElementoGrafo origen,
            ElementoGrafo destino,
            TipoRelacion relacion) {

        return switch (relacion) {

            case CONTIENE -> (origen instanceof Evento
                    && destino instanceof Jornada)
                    ||
                    (origen instanceof Jornada
                            && destino instanceof Actividad);

            case TIENE_SUBACTIVIDAD -> origen instanceof Actividad
                    && destino instanceof SubActividad;

            case TIENE_RESPONSABLE -> (origen instanceof Actividad
                    || origen instanceof SubActividad)
                    && destino instanceof Responsable;

            case TIENE_PARTICIPANTE -> (origen instanceof Actividad
                    || origen instanceof SubActividad)
                    && destino instanceof Participante;

            case DEPENDE_DE -> origen instanceof Actividad
                    && destino instanceof Actividad;

            case RELACIONADA_CON -> origen instanceof Actividad
                    && destino instanceof Actividad;
        };
    }

    public void conectar(
            String idOrigen,
            String idDestino,
            TipoRelacion relacion) {

        Vertice origen = buscarVertice(idOrigen);
        Vertice destino = buscarVertice(idDestino);

        if (origen == null || destino == null) {
            throw new IllegalArgumentException(
                    "No se encontró una de las entidades"
            );
        }

        if (!relacionValida(
                origen.getEntidad(),
                destino.getEntidad(),
                relacion)) {

            throw new IllegalArgumentException(
                    "La relación " + relacion
                            + " no es válida entre estas entidades"
            );
        }

        boolean existe = origen.getConexiones()
                .stream()
                .anyMatch(arista ->
                        arista.getDestino()
                                .getEntidad()
                                .getId()
                                .equals(idDestino)
                                && relacion == arista.getTipoRelacion()
                );

        if (existe) {
            throw new IllegalArgumentException(
                    "La relación ya existe"
            );
        }

        Arista arista = new Arista(destino, relacion);

        origen.agregarConexion(arista);
    }

    public void mostrarConexiones(String id) {

        Vertice vertice = buscarVertice(id);

        if (vertice == null) {
            System.out.println("Entidad no encontrada");
            return;
        }

        System.out.println("\nConexiones de: " + vertice);

        for (Arista arista : vertice.getConexiones()) {

            System.out.println(
                    "  --" + arista.getTipoRelacion()
                            + "--> " + arista.getDestino()
            );
        }
    }

    public void mostrarGrafo() {

        System.out.println("\n========== GRAFO DE EVENTOS ==========");

        for (Vertice vertice : vertices.values()) {

            System.out.println("\n" + vertice);

            for (Arista arista : vertice.getConexiones()) {

                System.out.println(
                        "   --" + arista.getTipoRelacion()
                                + "--> " + arista.getDestino()
                );
            }
        }
    }

    public void DFS(String idInicial) {

        Vertice inicial = buscarVertice(idInicial);

        if (inicial == null) {
            System.out.println("Entidad no encontrada");
            return;
        }

        Set<String> visitados = new HashSet<>();

        System.out.println("\n========== RECORRIDO DFS ==========");

        dfsRecursivo(inicial, visitados);
    }

    private void dfsRecursivo(
            Vertice actual,
            Set<String> visitados) {

        visitados.add(actual.getEntidad().getId());

        System.out.println(actual);

        for (Arista arista : actual.getConexiones()) {

            Vertice siguiente = arista.getDestino();

            String id = siguiente.getEntidad().getId();

            if (!visitados.contains(id)) {

                dfsRecursivo(siguiente, visitados);
            }
        }
    }

    public void BFS(String idInicial) {

        Vertice inicial = buscarVertice(idInicial);

        if (inicial == null) {
            System.out.println("Entidad no encontrada");
            return;
        }

        Queue<Vertice> cola = new LinkedList<>();

        Set<String> visitados = new HashSet<>();

        cola.offer(inicial);

        visitados.add(inicial.getEntidad().getId());

        System.out.println("\n========== RECORRIDO BFS ==========");

        while (!cola.isEmpty()) {

            Vertice actual = cola.poll();

            System.out.println(actual);

            for (Arista arista : actual.getConexiones()) {

                Vertice siguiente = arista.getDestino();

                String id = siguiente.getEntidad().getId();

                if (!visitados.contains(id)) {

                    visitados.add(id);

                    cola.offer(siguiente);
                }
            }
        }
    }

    public int cantidadVertices() {
        return vertices.size();
    }

    public int cantidadAristas() {
        int total = 0;

        for (Vertice vertice : vertices.values()) {
            total += vertice.getConexiones().size();
        }

        return total;
    }
}
