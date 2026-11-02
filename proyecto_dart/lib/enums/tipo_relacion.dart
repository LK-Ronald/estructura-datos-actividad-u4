enum TipoRelacion {
  contiene,
  tieneSubactividad,
  tieneResponsable,
  tieneParticipante,
  dependeDe,
  relacionadaCon,
}

extension TipoRelacionEtiqueta on TipoRelacion {
  String get etiqueta => switch (this) {
    TipoRelacion.contiene => 'CONTIENE',
    TipoRelacion.tieneSubactividad => 'TIENE_SUBACTIVIDAD',
    TipoRelacion.tieneResponsable => 'TIENE_RESPONSABLE',
    TipoRelacion.tieneParticipante => 'TIENE_PARTICIPANTE',
    TipoRelacion.dependeDe => 'DEPENDE_DE',
    TipoRelacion.relacionadaCon => 'RELACIONADA_CON',
  };
}
