import '../grafo/elemento_grafo.dart';
import 'hora.dart';

class SubActividad implements ElementoGrafo {
  SubActividad(
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
  String toString() => 'Subactividad: $nombre';
}
