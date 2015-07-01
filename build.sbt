name := """smscenter"""

organization := "com.lvxingpai"

version := "0.2-SNAPSHOT"

crossScalaVersions := "2.10.4" :: "2.11.4" :: Nil

com.twitter.scrooge.ScroogeSBT.newSettings

scalariformSettings

val finagleVersion = "6.14.0"

libraryDependencies ++= Seq(
  "com.lvxingpai" %% "appconfig" % "0.1.1",
  "org.jsoup" % "jsoup" % "1.8.2",
  "com.twitter" %% "finagle-core" % finagleVersion,
  "com.twitter" %% "finagle-thrift" % finagleVersion,
  "com.twitter" %% "finagle-thriftmux" % finagleVersion,
  "com.twitter" %% "scrooge-core" % "3.16.3",
  "org.apache.thrift" % "libthrift" % "0.8.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
  "com.typesafe" % "config" % "1.2.1",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % "2.5.2",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.5.3"
)

publishTo := {
  val nexus = "http://nexus.lvxingpai.com/content/repositories/"
  if (isSnapshot.value)
    Some("publishSnapshots" at nexus + "snapshots")
  else
    Some("publishReleases"  at nexus + "releases")
}

val root = project.in(file(".")).enablePlugins(JavaAppPackaging)

Keys.mainClass in Compile := Some("com.lvxingpai.smscenter.SmsCenterServer")

fork in run := true
