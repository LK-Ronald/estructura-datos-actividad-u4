using ProyectoCSharp.Grafo;

namespace ProyectoCSharp.Entidades;

public sealed class Responsable(
    string id,
    string nombre,
    string correo,
    string telefono) : IElementoGrafo
{
    public string Id { get; } = id;
    public string Nombre { get; } = nombre;
    public string Correo { get; } = correo;
    public string Telefono { get; } = telefono;

    public override string ToString() => $"Responsable: {Nombre}";
}
