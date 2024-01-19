val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScoverageSbtPlugin)
  .settings(
    name := "chess",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    coverageExcludedPackages := "<empty>;view.*"
  )

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.17"
libraryDependencies += "org.scalatestplus" %% "mockito-5-8" % "3.2.17.0" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test

libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.2.0"

libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.3"

fork := true
connectInput := true

// JavaFX in Docker:
// javaOptions in run ++= Seq(
//   "--module-path",
//   "/opt/javafx/javafx-sdk-21/lib",
//   "--add-modules",
//   "javafx.controls,javafx.fxml,javafx.web"
// )
