class Hora {
  const Hora(this.hora, this.minuto);

  final int hora;
  final int minuto;

  factory Hora.parse(String valor) {
    final coincidencia = RegExp(r'^(\d{2}):(\d{2})$').firstMatch(valor);
    if (coincidencia == null) {
      throw FormatException('La hora debe tener formato HH:mm');
    }

    final hora = int.parse(coincidencia.group(1)!);
    final minuto = int.parse(coincidencia.group(2)!);
    if (hora > 23 || minuto > 59) {
      throw FormatException('La hora no es valida');
    }

    return Hora(hora, minuto);
  }

  @override
  String toString() =>
      '${hora.toString().padLeft(2, '0')}:${minuto.toString().padLeft(2, '0')}';
}
