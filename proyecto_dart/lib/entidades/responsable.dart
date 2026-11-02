import '../grafo/elemento_grafo.dart';

class Responsable implements ElementoGrafo {
  Responsable(this.id, this.nombre, this.correo, this.telefono);

  @override
  final String id;

  @override
  final String nombre;

  final String correo;
  final String telefono;

  @override
  String toString() => 'Responsable: $nombre';
}
