import sbt._

object Dependencies {
  val ZioVersion     = "2.0.15"
  val ZHTTPVersion   = "1.0.0-SNAPSHOT-RC10"
  val ZioJsonVersion = "0.6.0"

  val `zio-http`      = "io.d11" %% "zhttp" % ZHTTPVersion
  val `zio-http-test` = "io.d11" %% "zhttp" % ZHTTPVersion % Test

  val `zio-test`     = "dev.zio" %% "zio-test"     % ZioVersion % Test
  val `zio-test-sbt` = "dev.zio" %% "zio-test-sbt" % ZioVersion % Test

  val `zio-json` = "dev.zio" %% "zio-json" % ZioJsonVersion

  val quill = "io.getquill"          %% "quill-jdbc-zio"       % "4.6.1"
  val magic = "io.github.kitlangton" %% "zio-magic"            % "0.3.11"
  val mysql = "mysql"                 % "mysql-connector-java" % "8.0.33"

  val slf4j = "ch.qos.logback"        % "logback-classic" % "1.4.10"
}
