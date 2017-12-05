import sbt.url

organization := "io.github.definiti"

name := "glossary"

version := "0.2.0-snapshot"

scalaVersion := "2.12.4"

libraryDependencies += "io.github.definiti" %% "core" % "0.2.0-snapshot"
libraryDependencies += "commons-io" % "commons-io" % "2.5"
libraryDependencies += "com.typesafe" % "config" % "1.3.1"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25"
libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.0.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
libraryDependencies += "com.github.rjeschke" % "txtmark" % "0.13"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-language:implicitConversions", "-feature")

useGpg := true

pomIncludeRepository := { _ => false }

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

homepage := Some(url("https://definiti.github.io"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/definiti/definiti-glossary"),
    "scm:git@github.com:definiti/definiti-glossary.git"
  )
)

developers := List(
  Developer(
    id = "kneelnrise",
    name = "Gaëtan Rizio",
    email = "gaetan@rizio.fr",
    url = url("https://github.com/kneelnrise")
  )
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}