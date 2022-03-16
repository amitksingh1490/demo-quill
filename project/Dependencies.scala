import sbt._

object Dependencies {
  val ZioVersion   = "1.0.13"
  val ZHTTPVersion = "1.0.0.0-RC25"

  val `zio-http`      = "io.d11" %% "zhttp" % ZHTTPVersion
  val `zio-http-test` = "io.d11" %% "zhttp" % ZHTTPVersion % Test

  val `zio-test`     = "dev.zio"              %% "zio-test"             % ZioVersion % Test
  val `zio-test-sbt` = "dev.zio"              %% "zio-test-sbt"         % ZioVersion % Test
  val quill          = "io.getquill"          %% "quill-jdbc-zio"       % "3.12.0"
  val magic          = "io.github.kitlangton" %% "zio-magic"            % "0.3.11"
  val mysql          = "mysql"                 % "mysql-connector-java" % "8.0.17"
}
