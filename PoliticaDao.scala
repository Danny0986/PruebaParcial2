package dao

import doobie.*
import doobie.implicits.*
import models.Politica
import cats.implicits.*

object PoliticaDao {

  def insert(politica: Politica): ConnectionIO[Int] =
    sql"""
      INSERT INTO politica
      (ID, Candidato, Partido_Politico, Evento, Fecha_Evento, Ubicacion, Asistentes_Estimados, Campaña_Activa)
      VALUES (
        ${politica.ID},
        ${politica.Candidato},
        ${politica.Partido_Politico},
        ${politica.Evento},
        ${politica.Fecha_Evento.toString},
        ${politica.Ubicacion},
        ${politica.Asistentes_Estimados},
        ${politica.Campaña_Activa}
      )
    """.update.run

  def insertAll(politicas: List[Politica]): ConnectionIO[List[Int]] =
    import cats.implicits.*
    politicas.traverse(insert)
}
