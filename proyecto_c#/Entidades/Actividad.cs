using ProyectoCSharp.Grafo;

namespace ProyectoCSharp.Entidades;

public sealed class Actividad(
    string id,
    string nombre,
    string descripcion,
    TimeOnly horaInicio,
    TimeOnly horaFin) : IElementoGrafo
{
    public string Id { get; } = id;
    public string Nombre { get; } = nombre;
    public string Descripcion { get; } = descripcion;
    public TimeOnly HoraInicio { get; } = horaInicio;
    public TimeOnly HoraFin { get; } = horaFin;

    public override string ToString() => $"Actividad: {Nombre}";
}
