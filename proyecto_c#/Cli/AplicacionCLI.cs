using System.Globalization;
using ProyectoCSharp.Entidades;
using ProyectoCSharp.Enums;
using ProyectoCSharp.Grafo;

namespace ProyectoCSharp.Cli;

public sealed class AplicacionCLI
{
    private readonly ProyectoCSharp.Grafo.Grafo _grafo = new();

    public void Ejecutar()
    {
        while (true)
        {
            MostrarMenu();
            Console.Write("Selecciona una opcion: ");
            var opcion = Console.ReadLine()?.Trim();
            if (opcion is null)
            {
                return;
            }

            switch (opcion)
            {
                case "1":
                    _grafo.MostrarEntidades();
                    break;
                case "2":
                    _grafo.MostrarGrafo();
                    break;
                case "3":
                    _grafo.MostrarConexiones(SolicitarId("Ingresa el ID de la entidad: "));
                    break;
                case "4":
                    _grafo.DFS(SolicitarId("Ingresa el ID inicial para DFS: "));
                    break;
                case "5":
                    _grafo.BFS(SolicitarId("Ingresa el ID inicial para BFS: "));
                    break;
                case "6":
                    MostrarEstadisticas();
                    break;
                case "7":
                    AgregarEntidad();
                    break;
                case "8":
                    ListarEntidades<Evento>("EVENTOS");
                    break;
                case "9":
                    ListarEntidades<Participante>("PARTICIPANTES");
                    break;
                case "10":
                    ListarEntidades<Jornada>("JORNADAS");
                    break;
                case "11":
                    MostrarParticipante(SolicitarId("Ingresa el ID del participante: "));
                    break;
                case "12":
                    ListarEntidades<Responsable>("RESPONSABLES");
                    break;
                case "13":
                    ListarEntidades<SubActividad>("SUBACTIVIDADES");
                    break;
                case "14":
                    ListarEntidades<Actividad>("ACTIVIDADES");
                    break;
                case "15":
                    ConectarEntidades();
                    break;
                case "0":
                    Console.WriteLine("Gracias por usar el sistema. Hasta pronto.");
                    return;
                default:
                    Console.WriteLine("Opcion no valida. Selecciona una opcion del menu.");
                    break;
            }

            Console.WriteLine("\nPresiona Enter para continuar...");
            if (Console.ReadLine() is null)
            {
                return;
            }
        }
    }

    private static void MostrarMenu()
    {
        Console.WriteLine("\n========================================");
        Console.WriteLine("       SISTEMA DE GESTION DE EVENTOS");
        Console.WriteLine("========================================");
        Console.WriteLine("1. Listar entidades");
        Console.WriteLine("2. Mostrar grafo completo");
        Console.WriteLine("3. Consultar conexiones de una entidad");
        Console.WriteLine("4. Recorrer grafo en profundidad (DFS)");
        Console.WriteLine("5. Recorrer grafo en anchura (BFS)");
        Console.WriteLine("6. Ver estadisticas");
        Console.WriteLine("7. Agregar entidad");
        Console.WriteLine("8. Ver eventos");
        Console.WriteLine("9. Ver participantes");
        Console.WriteLine("10. Ver jornadas");
        Console.WriteLine("11. Ver participante por ID");
        Console.WriteLine("12. Ver responsables");
        Console.WriteLine("13. Ver subactividades");
        Console.WriteLine("14. Ver actividades");
        Console.WriteLine("15. Conectar entidades");
        Console.WriteLine("0. Salir");
        Console.WriteLine("========================================");
    }

    private static string SolicitarId(string mensaje)
    {
        Console.Write(mensaje);
        return Console.ReadLine()?.Trim() ?? "";
    }

    private void MostrarEstadisticas()
    {
        Console.WriteLine("\n========== ESTADISTICAS ==========");
        Console.WriteLine($"Total de entidades: {_grafo.CantidadVertices}");
        Console.WriteLine($"Total de relaciones: {_grafo.CantidadAristas}");
    }

    private void ListarEntidades<T>(string titulo) where T : class, IElementoGrafo
    {
        var entidades = _grafo.ObtenerEntidadesPorTipo<T>();
        Console.WriteLine($"\n========== {titulo} ==========");
        if (entidades.Count == 0)
        {
            Console.WriteLine("No hay entidades registradas de este tipo.");
            return;
        }

        foreach (var entidad in entidades)
        {
            Console.WriteLine($"{entidad.Id} - {entidad}");
        }
    }

    private void MostrarParticipante(string id)
    {
        if (_grafo.BuscarVertice(id)?.Entidad is not Participante participante)
        {
            Console.WriteLine($"No se encontro un participante con ID: {id}");
            return;
        }

        Console.WriteLine("\n========== PARTICIPANTE ==========");
        Console.WriteLine($"ID: {participante.Id}");
        Console.WriteLine($"Nombre: {participante.Nombre}");
        Console.WriteLine($"Correo: {participante.Correo}");
        Console.WriteLine($"Institucion: {participante.Institucion}");
    }

    private void ConectarEntidades()
    {
        Console.WriteLine("\n========== CONECTAR ENTIDADES ==========");
        Console.WriteLine("1. Agregar jornada a evento");
        Console.WriteLine("2. Agregar actividad a jornada");
        Console.WriteLine("3. Agregar actividad a subactividad");
        Console.WriteLine("4. Agregar responsable a actividad");
        Console.WriteLine("5. Agregar responsable a subactividad");
        Console.WriteLine("6. Agregar participante a actividad");

        var relacion = SolicitarId("Selecciona una opcion: ") switch
        {
            "1" or "2" => TipoRelacion.Contiene,
            "3" => TipoRelacion.TieneSubactividad,
            "4" or "5" => TipoRelacion.TieneResponsable,
            "6" => TipoRelacion.TieneParticipante,
            _ => (TipoRelacion?)null
        };
        if (relacion is null)
        {
            Console.WriteLine("Seleccion no valida.");
            return;
        }

        var idOrigen = SolicitarId("ID de la entidad de origen: ");
        var idDestino = SolicitarId("ID de la entidad de destino: ");
        try
        {
            _grafo.Conectar(idOrigen, idDestino, relacion.Value);
            Console.WriteLine("Entidades conectadas correctamente.");
        }
        catch (ArgumentException exception)
        {
            Console.WriteLine($"No se pudo crear la relacion: {exception.Message}");
        }
    }

    private void AgregarEntidad()
    {
        Console.WriteLine("\n========== AGREGAR ENTIDAD ==========");
        Console.WriteLine("1. Evento");
        Console.WriteLine("2. Jornada");
        Console.WriteLine("3. Actividad");
        Console.WriteLine("4. Subactividad");
        Console.WriteLine("5. Responsable");
        Console.WriteLine("6. Participante");

        try
        {
            switch (SolicitarId("Selecciona el tipo de entidad: "))
            {
                case "1":
                    _grafo.AgregarElemento(new Evento(
                        SolicitarId("ID: "),
                        SolicitarId("Nombre: "),
                        SolicitarId("Descripcion: "),
                        SolicitarFecha("Fecha de inicio (AAAA-MM-DD): "),
                        SolicitarFecha("Fecha de fin (AAAA-MM-DD): ")));
                    break;
                case "2":
                    _grafo.AgregarElemento(new Jornada(
                        SolicitarId("ID: "),
                        SolicitarId("Nombre: "),
                        SolicitarFecha("Fecha (AAAA-MM-DD): ")));
                    break;
                case "3":
                    _grafo.AgregarElemento(new Actividad(
                        SolicitarId("ID: "),
                        SolicitarId("Nombre: "),
                        SolicitarId("Descripcion: "),
                        SolicitarHora("Hora de inicio (HH:mm): "),
                        SolicitarHora("Hora de fin (HH:mm): ")));
                    break;
                case "4":
                    _grafo.AgregarElemento(new SubActividad(
                        SolicitarId("ID: "),
                        SolicitarId("Nombre: "),
                        SolicitarId("Descripcion: "),
                        SolicitarHora("Hora de inicio (HH:mm): "),
                        SolicitarHora("Hora de fin (HH:mm): ")));
                    break;
                case "5":
                    _grafo.AgregarElemento(new Responsable(
                        SolicitarId("ID: "),
                        SolicitarId("Nombre: "),
                        SolicitarId("Correo: "),
                        SolicitarId("Telefono: ")));
                    break;
                case "6":
                    _grafo.AgregarElemento(new Participante(
                        SolicitarId("ID: "),
                        SolicitarId("Nombre: "),
                        SolicitarId("Correo: "),
                        SolicitarId("Institucion: ")));
                    break;
                default:
                    Console.WriteLine("Tipo de entidad no valido.");
                    return;
            }

            Console.WriteLine("Entidad agregada correctamente.");
        }
        catch (FormatException)
        {
            Console.WriteLine("Fecha u hora no valida. Verifica el formato indicado.");
        }
        catch (ArgumentException exception)
        {
            Console.WriteLine($"No se pudo agregar la entidad: {exception.Message}");
        }
    }

    private static DateOnly SolicitarFecha(string mensaje) =>
        DateOnly.ParseExact(SolicitarId(mensaje), "yyyy-MM-dd", CultureInfo.InvariantCulture);

    private static TimeOnly SolicitarHora(string mensaje) =>
        TimeOnly.ParseExact(SolicitarId(mensaje), "HH:mm", CultureInfo.InvariantCulture);
}
