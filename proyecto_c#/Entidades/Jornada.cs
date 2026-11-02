using ProyectoCSharp.Grafo;

namespace ProyectoCSharp.Entidades;

public sealed class Jornada(string id, string nombre, DateOnly fecha) : IElementoGrafo
{
    public string Id { get; } = id;
    public string Nombre { get; } = nombre;
    public DateOnly Fecha { get; } = fecha;

    public override string ToString() => $"Jornada: {Nombre}";
}
