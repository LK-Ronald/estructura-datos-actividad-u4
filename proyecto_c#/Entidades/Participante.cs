using ProyectoCSharp.Grafo;

namespace ProyectoCSharp.Entidades;

public sealed class Participante(
    string id,
    string nombre,
    string correo,
    string institucion) : IElementoGrafo
{
    public string Id { get; } = id;
    public string Nombre { get; } = nombre;
    public string Correo { get; } = correo;
    public string Institucion { get; } = institucion;

    public override string ToString() => $"Participante: {Nombre}";
}
