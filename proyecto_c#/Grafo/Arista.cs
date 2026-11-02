using ProyectoCSharp.Enums;

namespace ProyectoCSharp.Grafo;

public sealed class Arista(Vertice destino, TipoRelacion tipoRelacion)
{
    public Vertice Destino { get; } = destino;
    public TipoRelacion TipoRelacion { get; } = tipoRelacion;
}
