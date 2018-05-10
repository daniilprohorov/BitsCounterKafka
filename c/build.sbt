name := "Consumer"

version := "0.1"

organization := "daniil" 
ThisBuild / scalaVersion := "2.11.5"

val flinkVersion = "1.4.2"

val flinkDependencies = Seq(
 // "org.apache.flink" %% "flink-scala" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion , 
  "org.apache.flink" %% "flink-connector-kafka-0.11" % flinkVersion)

  lazy val root = (project in file(".")).
  settings(
    libraryDependencies ++= flinkDependencies
  )

