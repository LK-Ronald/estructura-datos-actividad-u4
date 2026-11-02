import '../grafo/elemento_grafo.dart';

class Jornada implements ElementoGrafo {
  Jornada(this.id, this.nombre, this.fecha);

  @override
  final String id;

  @override
  final String nombre;

  final DateTime fecha;

  @override
  String toString() => 'Jornada: $nombre';
}
