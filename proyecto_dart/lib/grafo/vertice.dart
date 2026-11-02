import 'arista.dart';
import 'elemento_grafo.dart';

class Vertice {
  Vertice(this.entidad);

  final ElementoGrafo entidad;
  final List<Arista> conexiones = [];

  void agregarConexion(Arista arista) => conexiones.add(arista);

  @override
  String toString() => '${entidad.id} - ${entidad.nombre}';
}
