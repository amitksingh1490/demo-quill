import Dependencies._

// give the user a nice default project!
ThisBuild / organization := "com.example"
ThisBuild / version      := "1.0.0"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(BuildHelper.stdSettings)
  .settings(
    name := "Demo-Quill",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    libraryDependencies ++= Seq(
      `zio-test`,
      `zio-test-sbt`,
      `zio-http`,
      `zio-http-test`,
      `zio-json`,
      quill,
      magic,
      mysql,
      slf4j
    ),
    scalacOptions ++= Seq("-Ymacro-annotations"),
  )
  .settings(
    Docker / version          := version.value,
    Compile / run / mainClass := Option("com.example.zhttpservice.ZhttpService"),
  )

addCommandAlias("fmt", "scalafmt; Test / scalafmt; sFix;")
addCommandAlias("fmtCheck", "scalafmtCheck; Test / scalafmtCheck; sFixCheck")
addCommandAlias("sFix", "scalafix OrganizeImports; Test / scalafix OrganizeImports")
addCommandAlias(
  "sFixCheck",
  "scalafix --check OrganizeImports; Test / scalafix --check OrganizeImports",
)
