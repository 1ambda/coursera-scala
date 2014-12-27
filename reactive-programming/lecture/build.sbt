import sbt._
import Process._
import Keys._

name := "reactive-programming"

version := "1.0"

scalaVersion := "2.11.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "io.reactivex" %% "rxscala" % "0.22.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.8",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.8",
  "com.ning" % "async-http-client" % "1.9.3"
)

testOptions in Test += Tests.Argument("-oI")
