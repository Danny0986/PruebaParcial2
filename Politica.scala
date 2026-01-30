package models

import java.time.LocalDate

case class Politica(
              ID: Int,
              Candidato: String,
              Partido_Politico: String,
              Evento: String,
              Fecha_Evento: LocalDate,
              Ubicacion: String,
              Asistentes_Estimados: Int,
              Campaña_Activa: Boolean
              )