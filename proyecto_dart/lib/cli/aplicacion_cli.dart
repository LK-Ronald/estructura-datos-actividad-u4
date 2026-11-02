import 'dart:io';

import '../entidades/actividad.dart';
import '../entidades/evento.dart';
import '../entidades/hora.dart';
import '../entidades/jornada.dart';
import '../entidades/participante.dart';
import '../entidades/responsable.dart';
import '../entidades/subactividad.dart';
import '../enums/tipo_relacion.dart';
import '../grafo/elemento_grafo.dart';
import '../grafo/grafo.dart';

class AplicacionCli {
  final Grafo _grafo = Grafo();

  void ejecutar() {
    while (true) {
      _mostrarMenu();
      final opcion = _solicitarId('Selecciona una opcion: ');
      if (opcion == null) {
        return;
      }

      switch (opcion) {
        case '1':
          _grafo.mostrarEntidades();
        case '2':
          _grafo.mostrarGrafo();
        case '3':
          _grafo.mostrarConexiones(
            _solicitarId('Ingresa el ID de la entidad: ') ?? '',
          );
        case '4':
          _grafo.dfs(_solicitarId('Ingresa el ID inicial para DFS: ') ?? '');
        case '5':
          _grafo.bfs(_solicitarId('Ingresa el ID inicial para BFS: ') ?? '');
        case '6':
          _mostrarEstadisticas();
        case '7':
          _agregarEntidad();
        case '8':
          _listarEntidades<Evento>('EVENTOS');
        case '9':
          _listarEntidades<Participante>('PARTICIPANTES');
        case '10':
          _listarEntidades<Jornada>('JORNADAS');
        case '11':
          _mostrarParticipante(
            _solicitarId('Ingresa el ID del participante: ') ?? '',
          );
        case '12':
          _listarEntidades<Responsable>('RESPONSABLES');
        case '13':
          _listarEntidades<SubActividad>('SUBACTIVIDADES');
        case '14':
          _listarEntidades<Actividad>('ACTIVIDADES');
        case '15':
          _conectarEntidades();
        case '0':
          print('Gracias por usar el sistema. Hasta pronto.');
          return;
        default:
          print('Opcion no valida. Selecciona una opcion del menu.');
      }

      print('\nPresiona Enter para continuar...');
      if (stdin.readLineSync() == null) {
        return;
      }
    }
  }

  void _mostrarMenu() {
    print('\n========================================');
    print('       SISTEMA DE GESTION DE EVENTOS');
    print('========================================');
    print('1. Listar entidades');
    print('2. Mostrar grafo completo');
    print('3. Consultar conexiones de una entidad');
    print('4. Recorrer grafo en profundidad (DFS)');
    print('5. Recorrer grafo en anchura (BFS)');
    print('6. Ver estadisticas');
    print('7. Agregar entidad');
    print('8. Ver eventos');
    print('9. Ver participantes');
    print('10. Ver jornadas');
    print('11. Ver participante por ID');
    print('12. Ver responsables');
    print('13. Ver subactividades');
    print('14. Ver actividades');
    print('15. Conectar entidades');
    print('0. Salir');
    print('========================================');
  }

  String? _solicitarId(String mensaje) {
    stdout.write(mensaje);
    return stdin.readLineSync()?.trim();
  }

  void _mostrarEstadisticas() {
    print('\n========== ESTADISTICAS ==========');
    print('Total de entidades: ${_grafo.cantidadVertices}');
    print('Total de relaciones: ${_grafo.cantidadAristas}');
  }

  void _listarEntidades<T extends ElementoGrafo>(String titulo) {
    final entidades = _grafo.obtenerEntidadesPorTipo<T>();
    print('\n========== $titulo ==========');
    if (entidades.isEmpty) {
      print('No hay entidades registradas de este tipo.');
      return;
    }

    for (final entidad in entidades) {
      print('${entidad.id} - $entidad');
    }
  }

  void _mostrarParticipante(String id) {
    final entidad = _grafo.buscarVertice(id)?.entidad;
    if (entidad is! Participante) {
      print('No se encontro un participante con ID: $id');
      return;
    }

    print('\n========== PARTICIPANTE ==========');
    print('ID: ${entidad.id}');
    print('Nombre: ${entidad.nombre}');
    print('Correo: ${entidad.correo}');
    print('Institucion: ${entidad.institucion}');
  }

  void _conectarEntidades() {
    print('\n========== CONECTAR ENTIDADES ==========');
    print('1. Agregar jornada a evento');
    print('2. Agregar actividad a jornada');
    print('3. Agregar actividad a subactividad');
    print('4. Agregar responsable a actividad');
    print('5. Agregar responsable a subactividad');
    print('6. Agregar participante a actividad');

    final relacion = switch (_solicitarId('Selecciona una opcion: ')) {
      '1' || '2' => TipoRelacion.contiene,
      '3' => TipoRelacion.tieneSubactividad,
      '4' || '5' => TipoRelacion.tieneResponsable,
      '6' => TipoRelacion.tieneParticipante,
      _ => null,
    };
    if (relacion == null) {
      print('Seleccion no valida.');
      return;
    }

    final idOrigen = _solicitarId('ID de la entidad de origen: ') ?? '';
    final idDestino = _solicitarId('ID de la entidad de destino: ') ?? '';
    try {
      _grafo.conectar(idOrigen, idDestino, relacion);
      print('Entidades conectadas correctamente.');
    } on ArgumentError catch (error) {
      print('No se pudo crear la relacion: ${error.message}');
    }
  }

  void _agregarEntidad() {
    print('\n========== AGREGAR ENTIDAD ==========');
    print('1. Evento');
    print('2. Jornada');
    print('3. Actividad');
    print('4. Subactividad');
    print('5. Responsable');
    print('6. Participante');

    try {
      switch (_solicitarId('Selecciona el tipo de entidad: ')) {
        case '1':
          _grafo.agregarElemento(
            Evento(
              _solicitarId('ID: ') ?? '',
              _solicitarId('Nombre: ') ?? '',
              _solicitarId('Descripcion: ') ?? '',
              _solicitarFecha('Fecha de inicio (AAAA-MM-DD): '),
              _solicitarFecha('Fecha de fin (AAAA-MM-DD): '),
            ),
          );
        case '2':
          _grafo.agregarElemento(
            Jornada(
              _solicitarId('ID: ') ?? '',
              _solicitarId('Nombre: ') ?? '',
              _solicitarFecha('Fecha (AAAA-MM-DD): '),
            ),
          );
        case '3':
          _grafo.agregarElemento(
            Actividad(
              _solicitarId('ID: ') ?? '',
              _solicitarId('Nombre: ') ?? '',
              _solicitarId('Descripcion: ') ?? '',
              _solicitarHora('Hora de inicio (HH:mm): '),
              _solicitarHora('Hora de fin (HH:mm): '),
            ),
          );
        case '4':
          _grafo.agregarElemento(
            SubActividad(
              _solicitarId('ID: ') ?? '',
              _solicitarId('Nombre: ') ?? '',
              _solicitarId('Descripcion: ') ?? '',
              _solicitarHora('Hora de inicio (HH:mm): '),
              _solicitarHora('Hora de fin (HH:mm): '),
            ),
          );
        case '5':
          _grafo.agregarElemento(
            Responsable(
              _solicitarId('ID: ') ?? '',
              _solicitarId('Nombre: ') ?? '',
              _solicitarId('Correo: ') ?? '',
              _solicitarId('Telefono: ') ?? '',
            ),
          );
        case '6':
          _grafo.agregarElemento(
            Participante(
              _solicitarId('ID: ') ?? '',
              _solicitarId('Nombre: ') ?? '',
              _solicitarId('Correo: ') ?? '',
              _solicitarId('Institucion: ') ?? '',
            ),
          );
        default:
          print('Tipo de entidad no valido.');
          return;
      }

      print('Entidad agregada correctamente.');
    } on FormatException {
      print('Fecha u hora no valida. Verifica el formato indicado.');
    } on ArgumentError catch (error) {
      print('No se pudo agregar la entidad: ${error.message}');
    }
  }

  DateTime _solicitarFecha(String mensaje) =>
      DateTime.parse(_solicitarId(mensaje) ?? '');

  Hora _solicitarHora(String mensaje) =>
      Hora.parse(_solicitarId(mensaje) ?? '');
}
