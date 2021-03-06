import sbt.url

organization := "io.github.definiti"

name := "glossary"

version := "0.3.0-SNAPSHOT"

scalaVersion := "2.12.5"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "io.github.definiti" %% "core" % "0.3.0-SNAPSHOT"
libraryDependencies += "commons-io" % "commons-io" % "2.6"
libraryDependencies += "com.typesafe" % "config" % "1.3.2"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25"
libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.4.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
libraryDependencies += "com.github.rjeschke" % "txtmark" % "0.13"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

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