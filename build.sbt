name := """smscenter"""

organization := "com.lvxingpai"

version := "0.3"

scalaVersion := "2.11.4"

com.twitter.scrooge.ScroogeSBT.newSettings

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.8.2",
  "com.twitter" %% "finagle-thriftmux" % "6.30.0",
  "com.twitter" %% "scrooge-core" % "4.2.0",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.3",
  "com.typesafe" % "config" % "1.3.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3"
)

val root = project.in(file(".")).enablePlugins(JavaAppPackaging)

fork in run := true
