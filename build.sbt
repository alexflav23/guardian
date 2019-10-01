import sbt.Keys._
import sbt._

lazy val Versions = new {
  val util = "0.50.0"
  val scalatest = "3.0.5"
  val scalacheck = "1.14.2"
}

val sharedSettings: Seq[Def.Setting[_]] = Defaults.coreDefaultSettings ++ Seq(
  organization := "com.guardian",
  version := "0.1.0",
  scalaVersion := "2.12.8",
  resolvers ++= Seq(
    "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
    "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
    "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
    "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
    "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
    "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
    "Twitter Repository"               at "http://maven.twttr.com"
  ),
  scalacOptions ++= Seq(
    "-language:postfixOps",
    "-language:implicitConversions",
    "-language:reflectiveCalls",
    "-language:higherKinds",
    "-language:existentials",
    "-Xlint",
    "-deprecation",
    "-feature",
    "-unchecked"
   )
)

lazy val guardian = (project in file("."))
  .settings(sharedSettings: _*)
  .settings(
    name := "guardian-tests",
    logBuffered in Test := true,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % Versions.scalatest,
      "com.outworkers" %% "util-samplers" % Versions.util,
      "org.scalacheck" % "scalacheck" % Versions.scalacheck
    )
  )
