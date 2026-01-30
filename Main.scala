import cats.effect.{IO, IOApp}
import cats.effect.implicits.*
import fs2.io.file.{Files, Path}
import fs2.data.csv.*
import fs2.data.csv.generic.semiauto.*
import models.Politica
import dao.PoliticaDao
import db.Database
import doobie.implicits.*

object Main extends IOApp.Simple {

  private val path2DataFile = "src/main/resources/data/politica.csv"

  given CsvRowDecoder[Politica, String] = deriveCsvRowDecoder

  private def calificacionStream: fs2.Stream[IO, Politica] =
    Files[IO]
      .readAll(Path(path2DataFile))
      .through(fs2.text.utf8.decode)
      .through(decodeUsingHeaders[Politica](','))

  override def run: IO[Unit] =
    Database.mysqlTransactor(runtime).use { xa =>
        calificacionStream
          .evalMap { politica =>
            PoliticaDao
              .insert(politica)
              .transact(xa)
              .flatMap(_ =>
                IO.println(s"MySQL -> Insertado: ${politica.ID}")
              )
          }
          .compile
          .drain
      }
      .flatMap(_ => IO.println("Proceso de streaming en MySQL completado."))
      .handleErrorWith(e =>
        IO.println(s"Error en MySQL: ${e.getMessage}")
      )
}
