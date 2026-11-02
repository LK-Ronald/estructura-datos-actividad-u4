using ProyectoCSharp.Entidades;
using ProyectoCSharp.Enums;

namespace ProyectoCSharp.Grafo;

public sealed class Grafo
{
    private readonly Dictionary<string, Vertice> _vertices = [];

    public void AgregarElemento(IElementoGrafo? entidad)
    {
        if (entidad is null || string.IsNullOrWhiteSpace(entidad.Id))
        {
            throw new ArgumentException("La entidad o su identificador no son validos");
        }

        if (_vertices.ContainsKey(entidad.Id))
        {
            throw new ArgumentException($"Ya existe una entidad con ID: {entidad.Id}");
        }

        _vertices.Add(entidad.Id, new Vertice(entidad));
    }

    public Vertice? BuscarVertice(string id) =>
        _vertices.GetValueOrDefault(id);

    public List<T> ObtenerEntidadesPorTipo<T>() where T : class, IElementoGrafo =>
        _vertices.Values
            .Select(vertice => vertice.Entidad)
            .OfType<T>()
            .ToList();

    public void MostrarEntidades()
    {
        Console.WriteLine("\n========== ENTIDADES ==========");
        foreach (var vertice in _vertices.Values)
        {
            Console.WriteLine(vertice);
        }
    }

    private static bool RelacionValida(
        IElementoGrafo origen,
        IElementoGrafo destino,
        TipoRelacion relacion) =>
        relacion switch
        {
            TipoRelacion.Contiene =>
                (origen is Evento && destino is Jornada)
                || (origen is Jornada && destino is Actividad),
            TipoRelacion.TieneSubactividad =>
                origen is Actividad && destino is SubActividad,
            TipoRelacion.TieneResponsable =>
                (origen is Actividad || origen is SubActividad)
                && destino is Responsable,
            TipoRelacion.TieneParticipante =>
                (origen is Actividad || origen is SubActividad)
                && destino is Participante,
            TipoRelacion.DependeDe or TipoRelacion.RelacionadaCon =>
                origen is Actividad && destino is Actividad,
            _ => false
        };

    public void Conectar(string idOrigen, string idDestino, TipoRelacion relacion)
    {
        var origen = BuscarVertice(idOrigen);
        var destino = BuscarVertice(idDestino);
        if (origen is null || destino is null)
        {
            throw new ArgumentException("No se encontro una de las entidades");
        }

        if (!RelacionValida(origen.Entidad, destino.Entidad, relacion))
        {
            throw new ArgumentException(
                $"La relacion {relacion.Etiqueta()} no es valida entre estas entidades");
        }

        if (origen.Conexiones.Any(arista =>
                arista.Destino.Entidad.Id == idDestino
                && arista.TipoRelacion == relacion))
        {
            throw new ArgumentException("La relacion ya existe");
        }

        origen.AgregarConexion(new Arista(destino, relacion));
    }

    public void MostrarConexiones(string id)
    {
        var vertice = BuscarVertice(id);
        if (vertice is null)
        {
            Console.WriteLine("Entidad no encontrada");
            return;
        }

        Console.WriteLine($"\nConexiones de: {vertice}");
        foreach (var arista in vertice.Conexiones)
        {
            Console.WriteLine($"  --{arista.TipoRelacion.Etiqueta()}--> {arista.Destino}");
        }
    }

    public void MostrarGrafo()
    {
        Console.WriteLine("\n========== GRAFO DE EVENTOS ==========");
        foreach (var vertice in _vertices.Values)
        {
            Console.WriteLine($"\n{vertice}");
            foreach (var arista in vertice.Conexiones)
            {
                Console.WriteLine($"   --{arista.TipoRelacion.Etiqueta()}--> {arista.Destino}");
            }
        }
    }

    public void DFS(string idInicial)
    {
        var inicial = BuscarVertice(idInicial);
        if (inicial is null)
        {
            Console.WriteLine("Entidad no encontrada");
            return;
        }

        Console.WriteLine("\n========== RECORRIDO DFS ==========");
        DfsRecursivo(inicial, []);
    }

    private static void DfsRecursivo(Vertice actual, HashSet<string> visitados)
    {
        visitados.Add(actual.Entidad.Id);
        Console.WriteLine(actual);
        foreach (var arista in actual.Conexiones)
        {
            var siguiente = arista.Destino;
            if (visitados.Add(siguiente.Entidad.Id))
            {
                DfsRecursivo(siguiente, visitados);
            }
        }
    }

    public void BFS(string idInicial)
    {
        var inicial = BuscarVertice(idInicial);
        if (inicial is null)
        {
            Console.WriteLine("Entidad no encontrada");
            return;
        }

        var cola = new Queue<Vertice>();
        var visitados = new HashSet<string> { inicial.Entidad.Id };
        cola.Enqueue(inicial);
        Console.WriteLine("\n========== RECORRIDO BFS ==========");

        while (cola.Count > 0)
        {
            var actual = cola.Dequeue();
            Console.WriteLine(actual);
            foreach (var arista in actual.Conexiones)
            {
                var siguiente = arista.Destino;
                if (visitados.Add(siguiente.Entidad.Id))
                {
                    cola.Enqueue(siguiente);
                }
            }
        }
    }

    public int CantidadVertices => _vertices.Count;
    public int CantidadAristas => _vertices.Values.Sum(v => v.Conexiones.Count);
}
