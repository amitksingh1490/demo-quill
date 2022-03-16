package com.example.demoquill

import com.example.demoquill.QuillContext._
import com.example.demoquill.api.GlobalApi
import com.example.demoquill.service.UserService
import zhttp.service.server.ServerChannelFactory
import zhttp.service.{EventLoopGroup, Server}
import zio._
import zio.magic._

import scala.util.Try

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
        UserService.live,
      )
      .exitCode
  }
}
