import '../enums/tipo_relacion.dart';
import 'vertice.dart';

class Arista {
  Arista(this.destino, this.tipoRelacion);

  final Vertice destino;
  final TipoRelacion tipoRelacion;
}
