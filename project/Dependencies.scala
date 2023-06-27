import sbt._

object Dependencies {
  val ZioVersion     = "1.0.13"
  val ZHTTPVersion   = "1.0.0.0-RC25"
  val ZioJsonVersion = "0.5.0"

  val `zio-http`      = "io.d11" %% "zhttp" % ZHTTPVersion
  val `zio-http-test` = "io.d11" %% "zhttp" % ZHTTPVersion % Test

  val `zio-test`     = "dev.zio" %% "zio-test"     % ZioVersion % Test
  val `zio-test-sbt` = "dev.zio" %% "zio-test-sbt" % ZioVersion % Test

  val `zio-json` = "dev.zio" %% "zio-json" % ZioJsonVersion

  val quill = "io.getquill"          %% "quill-jdbc-zio"       % "3.16.3"
  val magic = "io.github.kitlangton" %% "zio-magic"            % "0.3.11"
  val mysql = "mysql"                 % "mysql-connector-java" % "8.0.28"

  val slf4j = "ch.qos.logback"        % "logback-classic" % "1.4.8"
}
