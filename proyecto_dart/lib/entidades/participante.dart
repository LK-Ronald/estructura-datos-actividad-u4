import '../grafo/elemento_grafo.dart';

class Participante implements ElementoGrafo {
  Participante(this.id, this.nombre, this.correo, this.institucion);

  @override
  final String id;

  @override
  final String nombre;

  final String correo;
  final String institucion;

  @override
  String toString() => 'Participante: $nombre';
}
