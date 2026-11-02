import '../grafo/elemento_grafo.dart';
import 'hora.dart';

class Actividad implements ElementoGrafo {
  Actividad(
    this.id,
    this.nombre,
    this.descripcion,
    this.horaInicio,
    this.horaFin,
  );

  @override
  final String id;

  @override
  final String nombre;

  final String descripcion;
  final Hora horaInicio;
  final Hora horaFin;

  @override
  String toString() => 'Actividad: $nombre';
}
