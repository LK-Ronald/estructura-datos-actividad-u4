import 'dart:collection';

import '../entidades/actividad.dart';
import '../entidades/evento.dart';
import '../entidades/jornada.dart';
import '../entidades/participante.dart';
import '../entidades/responsable.dart';
import '../entidades/subactividad.dart';
import '../enums/tipo_relacion.dart';
import 'arista.dart';
import 'elemento_grafo.dart';
import 'vertice.dart';

class Grafo {
  final Map<String, Vertice> _vertices = {};

  int get cantidadVertices => _vertices.length;

  int get cantidadAristas => _vertices.values.fold(
    0,
    (total, vertice) => total + vertice.conexiones.length,
  );

  void agregarElemento(ElementoGrafo? entidad) {
    if (entidad == null || entidad.id.trim().isEmpty) {
      throw ArgumentError('La entidad o su identificador no son validos');
    }
    if (_vertices.containsKey(entidad.id)) {
      throw ArgumentError('Ya existe una entidad con ID: ${entidad.id}');
    }

    _vertices[entidad.id] = Vertice(entidad);
  }

  Vertice? buscarVertice(String id) => _vertices[id];

  List<T> obtenerEntidadesPorTipo<T extends ElementoGrafo>() => _vertices.values
      .map((vertice) => vertice.entidad)
      .whereType<T>()
      .toList();

  void mostrarEntidades() {
    print('\n========== ENTIDADES ==========');
    for (final vertice in _vertices.values) {
      print(vertice);
    }
  }

  bool _relacionValida(
    ElementoGrafo origen,
    ElementoGrafo destino,
    TipoRelacion relacion,
  ) {
    return switch (relacion) {
      TipoRelacion.contiene =>
        (origen is Evento && destino is Jornada) ||
            (origen is Jornada && destino is Actividad),
      TipoRelacion.tieneSubactividad =>
        origen is Actividad && destino is SubActividad,
      TipoRelacion.tieneResponsable =>
        (origen is Actividad || origen is SubActividad) &&
            destino is Responsable,
      TipoRelacion.tieneParticipante =>
        (origen is Actividad || origen is SubActividad) &&
            destino is Participante,
      TipoRelacion.dependeDe || TipoRelacion.relacionadaCon =>
        origen is Actividad && destino is Actividad,
    };
  }

  void conectar(String idOrigen, String idDestino, TipoRelacion relacion) {
    final origen = buscarVertice(idOrigen);
    final destino = buscarVertice(idDestino);
    if (origen == null || destino == null) {
      throw ArgumentError('No se encontro una de las entidades');
    }
    if (!_relacionValida(origen.entidad, destino.entidad, relacion)) {
      throw ArgumentError(
        'La relacion ${relacion.etiqueta} no es valida entre estas entidades',
      );
    }
    if (origen.conexiones.any(
      (arista) =>
          arista.destino.entidad.id == idDestino &&
          arista.tipoRelacion == relacion,
    )) {
      throw ArgumentError('La relacion ya existe');
    }

    origen.agregarConexion(Arista(destino, relacion));
  }

  void mostrarConexiones(String id) {
    final vertice = buscarVertice(id);
    if (vertice == null) {
      print('Entidad no encontrada');
      return;
    }

    print('\nConexiones de: $vertice');
    for (final arista in vertice.conexiones) {
      print('  --${arista.tipoRelacion.etiqueta}--> ${arista.destino}');
    }
  }

  void mostrarGrafo() {
    print('\n========== GRAFO DE EVENTOS ==========');
    for (final vertice in _vertices.values) {
      print('\n$vertice');
      for (final arista in vertice.conexiones) {
        print('   --${arista.tipoRelacion.etiqueta}--> ${arista.destino}');
      }
    }
  }

  void dfs(String idInicial) {
    final inicial = buscarVertice(idInicial);
    if (inicial == null) {
      print('Entidad no encontrada');
      return;
    }

    print('\n========== RECORRIDO DFS ==========');
    _dfsRecursivo(inicial, <String>{});
  }

  void _dfsRecursivo(Vertice actual, Set<String> visitados) {
    visitados.add(actual.entidad.id);
    print(actual);
    for (final arista in actual.conexiones) {
      final siguiente = arista.destino;
      if (visitados.add(siguiente.entidad.id)) {
        _dfsRecursivo(siguiente, visitados);
      }
    }
  }

  void bfs(String idInicial) {
    final inicial = buscarVertice(idInicial);
    if (inicial == null) {
      print('Entidad no encontrada');
      return;
    }

    final cola = Queue<Vertice>()..add(inicial);
    final visitados = <String>{inicial.entidad.id};
    print('\n========== RECORRIDO BFS ==========');
    while (cola.isNotEmpty) {
      final actual = cola.removeFirst();
      print(actual);
      for (final arista in actual.conexiones) {
        final siguiente = arista.destino;
        if (visitados.add(siguiente.entidad.id)) {
          cola.add(siguiente);
        }
      }
    }
  }
}
