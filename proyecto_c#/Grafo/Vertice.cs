namespace ProyectoCSharp.Grafo;

public sealed class Vertice(IElementoGrafo entidad)
{
    public IElementoGrafo Entidad { get; } = entidad;
    public List<Arista> Conexiones { get; } = [];

    public void AgregarConexion(Arista arista) => Conexiones.Add(arista);

    public override string ToString() => $"{Entidad.Id} - {Entidad.Nombre}";
}
