import sbt._
import Process._
import Keys._

name := "reactive-programming"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "io.reactivex" %% "rxscala" % "0.22.0"
)

testOptions in Test += Tests.Argument("-oI")
