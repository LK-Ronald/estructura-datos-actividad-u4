using ProyectoCSharp.Grafo;

namespace ProyectoCSharp.Entidades;

public sealed class Evento(
    string id,
    string nombre,
    string descripcion,
    DateOnly fechaInicio,
    DateOnly fechaFin) : IElementoGrafo
{
    public string Id { get; } = id;
    public string Nombre { get; } = nombre;
    public string Descripcion { get; } = descripcion;
    public DateOnly FechaInicio { get; } = fechaInicio;
    public DateOnly FechaFin { get; } = fechaFin;

    public override string ToString() => $"Evento: {Nombre}";
}
