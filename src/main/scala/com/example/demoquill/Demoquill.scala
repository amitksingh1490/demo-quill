package com.example.demoquill

import com.example.demoquill.QuillContext._
import com.example.demoquill.api.GlobalApi
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
import zio.json._

case class VampireResearch(suspiciousAge: Int)

@accessible
trait DataService {
  def usersWithAddress: IO[SQLException, List[(Person, Address)]]

  def users: IO[SQLException, List[Person]]
}

object DataService {
  val live = (DataServiceLive.apply _).toLayer[DataService]
}

final case class DataServiceLive(
    dataSource: DataSource,
    vampireResearch: VampireResearch,
  ) extends DataService {
  val env = Has(dataSource)

  def usersWithAddress: IO[SQLException, List[(Person, Address)]] =
    run {
      for {
        users   <- Queries.personsOlderThan(lift(vampireResearch.suspiciousAge))
        address <- query[Address].join(address => address.ownerFk == users.id)
      } yield (users, address)
    }.provide(env)

  def users: IO[SQLException, List[Person]] =
    run {
      Queries.persons
    }.provide(env)
}

object Demoquill extends App {
  private val server =
    Server.port(8090) ++              // Setup port
      Server.paranoidLeakDetection ++ // Paranoid leak detection (affects performance)
      Server.app(GlobalApi.api)       // Setup the Http app

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
