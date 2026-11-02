import '../grafo/elemento_grafo.dart';

class Evento implements ElementoGrafo {
  Evento(
    this.id,
    this.nombre,
    this.descripcion,
    this.fechaInicio,
    this.fechaFin,
  );

  @override
  final String id;

  @override
  final String nombre;

  final String descripcion;
  final DateTime fechaInicio;
  final DateTime fechaFin;

  @override
  String toString() => 'Evento: $nombre';
}
