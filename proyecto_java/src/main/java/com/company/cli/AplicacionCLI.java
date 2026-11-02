package com.company.cli;

import com.company.entities.Actividad;
import com.company.entities.Evento;
import com.company.entities.Jornada;
import com.company.entities.Participante;
import com.company.entities.Responsable;
import com.company.entities.SubActividad;
import com.company.enums.TipoRelacion;
import com.company.grafo.ElementoGrafo;
import com.company.grafo.Grafo;
import com.company.grafo.Vertice;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AplicacionCLI {
    private final Grafo grafo;

    public AplicacionCLI() {
        this.grafo = crearGrafoEjemplo();
    }

    public void ejecutar() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean ejecutando = true;

            while (ejecutando) {
                mostrarMenu();
                System.out.print("Selecciona una opcion: ");

                if (!scanner.hasNextLine()) {
                    break;
                }

                String opcion = scanner.nextLine().trim();

                switch (opcion) {
                    case "1" -> grafo.mostrarEntidades();
                    case "2" -> grafo.mostrarGrafo();
                    case "3" -> grafo.mostrarConexiones(
                            solicitarId(scanner, "Ingresa el ID de la entidad: ")
                    );
                    case "4" -> grafo.DFS(
                            solicitarId(scanner, "Ingresa el ID inicial para DFS: ")
                    );
                    case "5" -> grafo.BFS(
                            solicitarId(scanner, "Ingresa el ID inicial para BFS: ")
                    );
                    case "6" -> mostrarEstadisticas();
                    case "7" -> agregarEntidad(scanner);
                    case "8" -> listarEntidades(Evento.class, "EVENTOS");
                    case "9" -> listarEntidades(Participante.class, "PARTICIPANTES");
                    case "10" -> listarEntidades(Jornada.class, "JORNADAS");
                    case "11" -> mostrarParticipante(
                            solicitarId(scanner, "Ingresa el ID del participante: ")
                    );
                    case "12" -> listarEntidades(Responsable.class, "RESPONSABLES");
                    case "13" -> listarEntidades(SubActividad.class, "SUBACTIVIDADES");
                    case "14" -> listarEntidades(Actividad.class, "ACTIVIDADES");
                    case "15" -> conectarEntidades(scanner);
                    case "0" -> {
                        System.out.println("Gracias por usar el sistema. Hasta pronto.");
                        ejecutando = false;
                    }
                    default -> System.out.println(
                            "Opcion no valida. Selecciona una opcion del menu."
                    );
                }

                if (ejecutando) {
                    System.out.println("\nPresiona Enter para continuar...");
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                    scanner.nextLine();
                }
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n========================================");
        System.out.println("       SISTEMA DE GESTION DE EVENTOS");
        System.out.println("========================================");
        System.out.println("1. Listar entidades");
        System.out.println("2. Mostrar grafo completo");
        System.out.println("3. Consultar conexiones de una entidad");
        System.out.println("4. Recorrer grafo en profundidad (DFS)");
        System.out.println("5. Recorrer grafo en anchura (BFS)");
        System.out.println("6. Ver estadisticas");
        System.out.println("7. Agregar entidad");
        System.out.println("8. Ver eventos");
        System.out.println("9. Ver participantes");
        System.out.println("10. Ver jornadas");
        System.out.println("11. Ver participante por ID");
        System.out.println("12. Ver responsables");
        System.out.println("13. Ver subactividades");
        System.out.println("14. Ver actividades");
        System.out.println("15. Conectar entidades");
        System.out.println("0. Salir");
        System.out.println("========================================");
    }

    private String solicitarId(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    private void mostrarEstadisticas() {
        System.out.println("\n========== ESTADISTICAS ==========");
        System.out.println("Total de entidades: " + grafo.cantidadVertices());
        System.out.println("Total de relaciones: " + grafo.cantidadAristas());
    }

    private <T extends ElementoGrafo> void listarEntidades(
            Class<T> tipo,
            String titulo) {
        List<T> entidades = grafo.obtenerEntidadesPorTipo(tipo);
        System.out.println("\n========== " + titulo + " ==========");

        if (entidades.isEmpty()) {
            System.out.println("No hay entidades registradas de este tipo.");
            return;
        }

        for (T entidad : entidades) {
            System.out.println(entidad.getId() + " - " + entidad);
        }
    }

    private void mostrarParticipante(String id) {
        Vertice vertice = grafo.buscarVertice(id);

        if (vertice == null || !(vertice.getEntidad() instanceof Participante participante)) {
            System.out.println("No se encontro un participante con ID: " + id);
            return;
        }

        System.out.println("\n========== PARTICIPANTE ==========");
        System.out.println("ID: " + participante.getId());
        System.out.println("Nombre: " + participante.getNombre());
        System.out.println("Correo: " + participante.getCorreo());
        System.out.println("Institucion: " + participante.getInstitucion());
    }

    private void conectarEntidades(Scanner scanner) {
        System.out.println("\n========== CONECTAR ENTIDADES ==========");
        System.out.println("1. Agregar jornada a evento");
        System.out.println("2. Agregar actividad a jornada");
        System.out.println("3. Agregar actividad a subactividad");
        System.out.println("4. Agregar responsable a actividad");
        System.out.println("5. Agregar responsable a subactividad");
        System.out.println("6. Agregar participante a actividad");
        String opcion = solicitarId(scanner, "Selecciona una opcion: ");

        TipoRelacion relacion = switch (opcion) {
            case "1", "2" -> TipoRelacion.CONTIENE;
            case "3" -> TipoRelacion.TIENE_SUBACTIVIDAD;
            case "4", "5" -> TipoRelacion.TIENE_RESPONSABLE;
            case "6" -> TipoRelacion.TIENE_PARTICIPANTE;
            default -> null;
        };

        if (relacion == null) {
            System.out.println("Seleccion no valida.");
            return;
        }

        String idOrigen = solicitarId(scanner, "ID de la entidad de origen: ");
        String idDestino = solicitarId(scanner, "ID de la entidad de destino: ");

        try {
            grafo.conectar(idOrigen, idDestino, relacion);
            System.out.println("Entidades conectadas correctamente.");
        } catch (IllegalArgumentException exception) {
            System.out.println("No se pudo crear la relacion: " + exception.getMessage());
        }
    }

    private void agregarEntidad(Scanner scanner) {
        System.out.println("\n========== AGREGAR ENTIDAD ==========");
        System.out.println("1. Evento");
        System.out.println("2. Jornada");
        System.out.println("3. Actividad");
        System.out.println("4. Subactividad");
        System.out.println("5. Responsable");
        System.out.println("6. Participante");
        String tipo = solicitarId(scanner, "Selecciona el tipo de entidad: ");

        try {
            switch (tipo) {
                case "1" -> grafo.agregarElemento(new Evento(
                        solicitarId(scanner, "ID: "),
                        solicitarId(scanner, "Nombre: "),
                        solicitarId(scanner, "Descripcion: "),
                        solicitarFecha(scanner, "Fecha de inicio (AAAA-MM-DD): "),
                        solicitarFecha(scanner, "Fecha de fin (AAAA-MM-DD): ")
                ));
                case "2" -> grafo.agregarElemento(new Jornada(
                        solicitarId(scanner, "ID: "),
                        solicitarId(scanner, "Nombre: "),
                        solicitarFecha(scanner, "Fecha (AAAA-MM-DD): ")
                ));
                case "3" -> grafo.agregarElemento(new Actividad(
                        solicitarId(scanner, "ID: "),
                        solicitarId(scanner, "Nombre: "),
                        solicitarId(scanner, "Descripcion: "),
                        solicitarHora(scanner, "Hora de inicio (HH:mm): "),
                        solicitarHora(scanner, "Hora de fin (HH:mm): ")
                ));
                case "4" -> grafo.agregarElemento(new SubActividad(
                        solicitarId(scanner, "ID: "),
                        solicitarId(scanner, "Nombre: "),
                        solicitarId(scanner, "Descripcion: "),
                        solicitarHora(scanner, "Hora de inicio (HH:mm): "),
                        solicitarHora(scanner, "Hora de fin (HH:mm): ")
                ));
                case "5" -> grafo.agregarElemento(new Responsable(
                        solicitarId(scanner, "ID: "),
                        solicitarId(scanner, "Nombre: "),
                        solicitarId(scanner, "Correo: "),
                        solicitarId(scanner, "Telefono: ")
                ));
                case "6" -> grafo.agregarElemento(new Participante(
                        solicitarId(scanner, "ID: "),
                        solicitarId(scanner, "Nombre: "),
                        solicitarId(scanner, "Correo: "),
                        solicitarId(scanner, "Institucion: ")
                ));
                default -> {
                    System.out.println("Tipo de entidad no valido.");
                    return;
                }
            }

            System.out.println("Entidad agregada correctamente.");
        } catch (DateTimeParseException exception) {
            System.out.println("Fecha u hora no valida. Verifica el formato indicado.");
        } catch (IllegalArgumentException exception) {
            System.out.println("No se pudo agregar la entidad: " + exception.getMessage());
        }
    }

    private LocalDate solicitarFecha(Scanner scanner, String mensaje) {
        return LocalDate.parse(solicitarId(scanner, mensaje));
    }

    private LocalTime solicitarHora(Scanner scanner, String mensaje) {
        return LocalTime.parse(solicitarId(scanner, mensaje));
    }

    private static Grafo crearGrafoEjemplo() {
        return new Grafo();

//        Evento evento = new Evento(
//                "E1",
//                "Congreso de Tecnologia 2026",
//                "Evento de tecnologia y desarrollo de software",
//                LocalDate.of(2026, 11, 10),
//                LocalDate.of(2026, 11, 11)
//        );
//
//        Jornada jornada1 = new Jornada(
//                "J1",
//                "Desarrollo de Software",
//                LocalDate.of(2026, 11, 10)
//        );
//
//        Jornada jornada2 = new Jornada(
//                "J2",
//                "Inteligencia Artificial",
//                LocalDate.of(2026, 11, 11)
//        );
//
//        Actividad actividad1 = new Actividad(
//                "A1",
//                "Conferencia de Java",
//                "Fundamentos del lenguaje Java",
//                LocalTime.of(8, 0),
//                LocalTime.of(10, 0)
//        );
//
//        Actividad actividad2 = new Actividad(
//                "A2",
//                "Taller de Spring Boot",
//                "Desarrollo de aplicaciones empresariales",
//                LocalTime.of(10, 30),
//                LocalTime.of(12, 0)
//        );
//
//        Actividad actividad3 = new Actividad(
//                "A3",
//                "Conferencia de Inteligencia Artificial",
//                "Introduccion a la inteligencia artificial",
//                LocalTime.of(9, 0),
//                LocalTime.of(11, 0)
//        );
//
//        SubActividad subactividad1 = new SubActividad(
//                "S1",
//                "Introduccion a Java",
//                "Conceptos fundamentales de Java",
//                LocalTime.of(8, 0),
//                LocalTime.of(8, 30)
//        );
//
//        SubActividad subactividad2 = new SubActividad(
//                "S2",
//                "Programacion Orientada a Objetos",
//                "Clases, objetos y herencia",
//                LocalTime.of(8, 30),
//                LocalTime.of(9, 0)
//        );
//
//        SubActividad subactividad3 = new SubActividad(
//                "S3",
//                "Desarrollo de una API REST",
//                "Creacion de una API con Spring Boot",
//                LocalTime.of(10, 30),
//                LocalTime.of(11, 30)
//        );
//
//        Responsable responsable1 = new Responsable(
//                "R1",
//                "Juan Perez",
//                "juan@correo.com",
//                "3001234567"
//        );
//
//        Responsable responsable2 = new Responsable(
//                "R2",
//                "Laura Martinez",
//                "laura@correo.com",
//                "3011234567"
//        );
//
//        Participante participante1 = new Participante(
//                "P1",
//                "Carlos Rodriguez",
//                "carlos@correo.com",
//                "Universidad A"
//        );
//
//        Participante participante2 = new Participante(
//                "P2",
//                "Maria Gonzalez",
//                "maria@correo.com",
//                "Universidad B"
//        );
//
//        Participante participante3 = new Participante(
//                "P3",
//                "Pedro Ramirez",
//                "pedro@correo.com",
//                "Universidad C"
//        );
//
//        grafo.agregarElemento(evento);
//        grafo.agregarElemento(jornada1);
//        grafo.agregarElemento(jornada2);
//        grafo.agregarElemento(actividad1);
//        grafo.agregarElemento(actividad2);
//        grafo.agregarElemento(actividad3);
//        grafo.agregarElemento(subactividad1);
//        grafo.agregarElemento(subactividad2);
//        grafo.agregarElemento(subactividad3);
//        grafo.agregarElemento(responsable1);
//        grafo.agregarElemento(responsable2);
//        grafo.agregarElemento(participante1);
//        grafo.agregarElemento(participante2);
//        grafo.agregarElemento(participante3);
//
//        grafo.conectar("E1", "J1", Agregar jornada al evento);
//        grafo.conectar("E1", "J2", TipoRelacion.CONTIENE);
//        grafo.conectar("J1", "A1", TipoRelacion.CONTIENE);
//        grafo.conectar("J1", "A2", TipoRelacion.CONTIENE);
//        grafo.conectar("J2", "A3", TipoRelacion.CONTIENE);
//        grafo.conectar("A1", "S1", TipoRelacion.TIENE_SUBACTIVIDAD);
//        grafo.conectar("A1", "S2", TipoRelacion.TIENE_SUBACTIVIDAD);
//        grafo.conectar("A2", "S3", TipoRelacion.TIENE_SUBACTIVIDAD);
//        grafo.conectar("A1", "R1", TipoRelacion.TIENE_RESPONSABLE);
//        grafo.conectar("A2", "R1", TipoRelacion.TIENE_RESPONSABLE);
//        grafo.conectar("A3", "R2", TipoRelacion.TIENE_RESPONSABLE);
//        grafo.conectar("S3", "R2", TipoRelacion.TIENE_RESPONSABLE);
//        grafo.conectar("A1", "P1", TipoRelacion.TIENE_PARTICIPANTE);
//        grafo.conectar("A1", "P2", TipoRelacion.TIENE_PARTICIPANTE);
//        grafo.conectar("A2", "P1", TipoRelacion.TIENE_PARTICIPANTE);
//        grafo.conectar("A3", "P2", TipoRelacion.TIENE_PARTICIPANTE);
//        grafo.conectar("A3", "P3", TipoRelacion.TIENE_PARTICIPANTE);
//        grafo.conectar("A2", "A1", TipoRelacion.DEPENDE_DE);

        /*

            Modifica la opcion 15, la cual al seleccionarla despliegue el siguiente menu, y que solo por consola
            le pida el ID de la entidad de origen y el ID de la entidad de destino el tipo de coleccion lo infiere.
            Agregar jornada a evento.
            Agregar Actividad a jornada.
            Agregar Actividad a subactividad.
            Agregar responsable a actividad.
            Agregar responsable a subactividad.
            Agregar participante a actividad.

         */

//        return grafo;
    }
}
