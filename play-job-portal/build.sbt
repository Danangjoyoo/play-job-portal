name := """play-job-portal"""
organization := "com.play.job-portal"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.11"

// Test options
javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

libraryDependencies += guice

// tweaking play framework on java 17
libraryDependencies ++= Seq(
  "com.google.inject"            % "guice"                % "5.1.0",
  "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0"
)

// config manager
libraryDependencies += "com.typesafe" % "config" % "1.4.2"

// add logger slf4j
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.28"

// add Java ORM Hibernate-JPA
libraryDependencies += "org.hibernate" % "hibernate-core" % "6.2.5.Final"
libraryDependencies += "jakarta.persistence" % "jakarta.persistence-api" % "3.1.0"

// add driver
libraryDependencies += "org.postgresql" % "postgresql" % "42.6.0"

// add JSON parser
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.11.4"

// add JWT handler
libraryDependencies += "io.jsonwebtoken" % "jjwt-api" % "0.11.1"
libraryDependencies += "io.jsonwebtoken" % "jjwt-impl" % "0.11.1"
libraryDependencies += "io.jsonwebtoken" % "jjwt-jackson" % "0.11.1"

// add Mongo Driver
libraryDependencies += "org.mongodb" % "mongodb-driver-sync" % "4.9.0"
libraryDependencies += "org.mongodb" % "bson" % "4.10.2"

// add Redis Driver
libraryDependencies += "redis.clients" % "jedis" % "4.4.0"

// add ES driver
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "8.9.0"

// add gRPC handler
libraryDependencies += "io.grpc" % "grpc-stub" % "1.57.1"

// add google protobuf
libraryDependencies += "com.google.protobuf" % "protobuf-java" % "3.22.2"
