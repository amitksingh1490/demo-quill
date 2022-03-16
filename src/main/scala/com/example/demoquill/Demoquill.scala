package com.example.demoquill

import com.example.demoquill.QuillContext._
import com.example.demoquill.model._
import zhttp.http._
import zhttp.service.server.ServerChannelFactory
import zhttp.service.{EventLoopGroup, Server}
import zio._
import zio.macros.accessible
import zio.magic._

import java.sql.SQLException
import javax.sql.DataSource
import scala.util.Try

case class VampireResearch(suspiciousAge: Int)

@accessible
trait DataService {
  def getVampiresWithAddresses: IO[SQLException, List[(Person, Address)]]

  def getVampires: IO[SQLException, List[Person]]
}

object DataService {
  val live = (DataServiceLive.apply _).toLayer[DataService]
}

final case class DataServiceLive(
    dataSource: DataSource,
    vampireResearch: VampireResearch,
  ) extends DataService {
  val env = Has(dataSource)

  def getVampiresWithAddresses: IO[SQLException, List[(Person, Address)]] =
    run {
      for {
        vampire <- Queries.personsOlderThan(lift(vampireResearch.suspiciousAge))
        address <- query[Address].join(address => address.ownerFk == vampire.id)
      } yield (vampire, address)
    }.provide(env)

  def getVampires: IO[SQLException, List[Person]] =
    run {
      Queries.personsOlderThan(lift(vampireResearch.suspiciousAge))
    }.provide(env)
}

object Demoquill extends App {
  val app = Http.collectZIO[Request] {
    case Method.GET -> !! / "text" =>
      DataService
        .getVampiresWithAddresses
        .map(
          _.map {
            case (vampire, address) =>
              s"${vampire.firstName} lives in ${address.street}"
          },
        )
        .map(x => Response.text(x.toString))
  }

  private val server =
    Server.port(8090) ++              // Setup port
      Server.paranoidLeakDetection ++ // Paranoid leak detection (affects performance)
      Server.app(app)                 // Setup the Http app

  // Run it like any simple app
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    // Configure thread count using CLI
    val nThreads: Int = args.headOption.flatMap(x => Try(x.toInt).toOption).getOrElse(0)

    // Create a new server
    server
      .make
      .use(start =>
        // Waiting for the server to start
        console.putStrLn(s"Server started on port ${start.port}")

        // Ensures the server doesn't die after printing
          *> ZIO.never,
      )
      .injectCustom(
        ServerChannelFactory.auto,
        EventLoopGroup.auto(nThreads),
        dataSourceLayer,
        DataService.live,
        ZLayer.succeed(VampireResearch(200)),
      )
      .exitCode
  }
}
