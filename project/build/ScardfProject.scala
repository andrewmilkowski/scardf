import sbt._

class ScardfProject(info: ProjectInfo) extends DefaultProject(info) {

lazy val JENA_VERSION  = "2.6.3"
lazy val JENA_ARQ_VERSION  = "2.8.4"
lazy val JODATIME_VERSION  = "1.6"
lazy val COMMONS_LOGGING_VERSION  = "1.1.1"
lazy val SCALA_SPECS_VERSION  = "1.6.5"
lazy val SCALA_CHECK_VERSION  = "1.8-SNAPSHOT"
lazy val JUNIT_VERSION = "4.7"

// Compiler settings

  override def compileOptions = super.compileOptions ++
    Seq("-deprecation",
        "-Xmigration",
        "-Xcheckinit",
        "-Xwarninit",
        "-encoding", "utf8")
        .map(x => CompileOption(x))
  override def javaCompileOptions = JavaCompileOption("-Xlint:unchecked") :: super.javaCompileOptions.toList

 // Repositories

  lazy val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository/"
  lazy val mavenRemote = "Remote Maven Repository" at "http://repo2.maven.org/maven2/"

// Dependencies 

  lazy val jena = "com.hp.hpl.jena" % "jena" % JENA_VERSION 
  
// TODO (unclear why jena_2.6.3-sources.jar is being pulled, the same for iri)
// we bring jar directly from maven
  lazy val jenaFix = "com.hp.hpl.jena" % "jena" % JENA_VERSION from "http://repo2.maven.org/maven2/com/hp/hpl/jena/jena/2.6.3/jena-2.6.3.jar" 
  lazy val jenaIriFix = "com.hp.hpl.jena" % "jena" % JENA_VERSION from "http://repo2.maven.org/maven2/com/hp/hpl/jena/iri/0.8/iri-0.8.jar"

  lazy val jenaArq = "com.hp.hpl.jena" % "arq" % JENA_ARQ_VERSION 
  lazy val jodatime = "joda-time" % "joda-time" % JODATIME_VERSION 
  lazy val commonslogging = "commons-logging" % "commons-logging" % COMMONS_LOGGING_VERSION 

// Dependencies (Test)

  lazy val junit = "junit" % "junit" % JUNIT_VERSION % "test->default"
  lazy val scalaSpecs = "org.scala-tools.testing" % "specs_2.8.0" % SCALA_SPECS_VERSION % "test->default"
  lazy val scalaCheck = "org.scala-tools.testing" % "scalacheck_2.8.0" % SCALA_CHECK_VERSION % "test->default"
}
