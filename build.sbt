name := "SparkStreamingPostgres"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.2.0",
  "org.apache.spark" % "spark-streaming_2.11" % "2.2.0",
  "org.apache.spark" % "spark-sql_2.11" % "2.2.0",
  "org.postgresql" % "postgresql" % "9.4-1200-jdbc41",
  "com.typesafe" % "config" % "1.3.1"
)