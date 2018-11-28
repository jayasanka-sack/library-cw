name := """library"""
organization := "com.iit"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"
libraryDependencies += guice
libraryDependencies += javaJdbc

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"

lazy val myProject = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)


