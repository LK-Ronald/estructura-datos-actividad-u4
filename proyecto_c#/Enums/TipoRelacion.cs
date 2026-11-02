namespace ProyectoCSharp.Enums;

public enum TipoRelacion
{
    Contiene,
    TieneSubactividad,
    TieneResponsable,
    TieneParticipante,
    DependeDe,
    RelacionadaCon
}

public static class TipoRelacionEtiqueta
{
    public static string Etiqueta(this TipoRelacion relacion) => relacion switch
    {
        TipoRelacion.Contiene => "CONTIENE",
        TipoRelacion.TieneSubactividad => "TIENE_SUBACTIVIDAD",
        TipoRelacion.TieneResponsable => "TIENE_RESPONSABLE",
        TipoRelacion.TieneParticipante => "TIENE_PARTICIPANTE",
        TipoRelacion.DependeDe => "DEPENDE_DE",
        TipoRelacion.RelacionadaCon => "RELACIONADA_CON",
        _ => throw new ArgumentOutOfRangeException(nameof(relacion), relacion, null)
    };
}
