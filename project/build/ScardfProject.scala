import sbt._

class ScardfProject(info: ProjectInfo) extends DefaultProject(info) {

lazy val JENA_VERSION  = "2.6.2"
lazy val ARQ_VERSION  = "2.8.3"
lazy val JODATIME_VERSION  = "1.6"
lazy val COMMONS_LOGGING_VERSION  = "1.1.1"
lazy val SCALA_SPECS_VERSION  = "1.6.2"
lazy val SCALA_CHECK_VERSION  = "1.7"

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

  lazy val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"
  lazy val mavenRemote = "Remote Maven Repository" at "http://repo1.maven.org/maven2"

// Dependencies (Compile)

  lazy val jena = "com.hp.hpl.jena" % "jena" % JENA_VERSION % "compile->default"
  lazy val arq = "com.hp.hpl.jena" % "arq" % ARQ_VERSION % "compile->default"
  lazy val jodatime = "joda-time" % "joda-time" % JODATIME_VERSION % "compile->default"
  lazy val commonslogging = "commons-logging" % "commons-logging" % COMMONS_LOGGING_VERSION % "compile->default"

// Dependencies (Test)

  lazy val junit = "junit" % "junit" % "4.7" % "test->default"
  lazy val scalaSpecs = "org.scala-tools.testing" % "specs_2.8.0.Beta1-RC8" % SCALA_SPECS_VERSION % "test->default"
  lazy val scalaCheck = "org.scala-tools.testing" % "scalacheck_2.8.0.RC7" % SCALA_CHECK_VERSION % "test->default"
}
