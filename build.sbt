homepage := Some(url("https://github.com/pjfanning/pekko-mock-scheduler"))

licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html"))

organization := "com.github.pjfanning"

name := "pekko-mock-scheduler"


// -------------------------------------------------------------------------------------------------------------------
// Variables
// -------------------------------------------------------------------------------------------------------------------

val pekkoVersion = "1.0.1"
val javaVersion = "1.8"
val mainScalaVersion = "2.13.11"


// -------------------------------------------------------------------------------------------------------------------
// Dependencies
// -------------------------------------------------------------------------------------------------------------------

libraryDependencies ++= Seq(
  "org.apache.pekko" %% "pekko-actor" % pekkoVersion,
  "org.scalatest" %% "scalatest" % "3.2.16" % Test,
  "org.scalatestplus" %% "mockito-4-11" % "3.2.16.0" % Test
)


// ---------------------------------------------------------------------------------------------------------------------
// Compiler settings
// ---------------------------------------------------------------------------------------------------------------------

crossScalaVersions := Seq(mainScalaVersion, "2.12.18", "3.3.0")

scalaVersion := mainScalaVersion

Compile / javacOptions ++= Seq(
  "-source", javaVersion,
  "-target", javaVersion,
  "-Xlint:unchecked",
  "-Xlint:deprecation")

scalacOptions ++= Seq(
  "-target:jvm-" + javaVersion,
  "-encoding", "UTF-8"
)

Compile / scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature",  // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xlint", // Enable recommended additional warnings.
  "-Ywarn-dead-code",
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
) ++ (if (!scalaVersion.value.startsWith("2.13")) Seq(
  "-Ywarn-adapted-args" // Warn if an argument list is modified to match the receiver.
) else Seq())

Test / scalacOptions ~= { (options: Seq[String]) =>
  options.filterNot(_ == "-Ywarn-value-discard").filterNot(_ == "-Ywarn-dead-code" /* to fix warnings due to Mockito */)
}


// ---------------------------------------------------------------------------------------------------------------------
// Sonatype settings
// ---------------------------------------------------------------------------------------------------------------------

sonatypeProfileName := "com.github.pjfanning"

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

Test / publishArtifact := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <scm>
    <connection>scm:git:git@github.com:pjfanning/pekko-mock-scheduler.git</connection>
    <developerConnection>scm:git:git@github.com:pjfanning/pekko-mock-scheduler.git</developerConnection>
    <url>git@github.com:pjfanning/pekko-mock-scheduler.git</url>
  </scm>
  <developers>
    <developer>
      <id>miguno</id>
      <name>Michael G. Noll</name>
      <url>http://www.michael-noll.com/</url>
    </developer>
    <developer>
      <id>pjfanning</id>
      <name>PJ Fanning</name>
      <url>https://github.com/pjfanning</url>
    </developer>
  </developers>)


// ---------------------------------------------------------------------------------------------------------------------
// Testing settings
// ---------------------------------------------------------------------------------------------------------------------

// Write test results to file in JUnit XML format
Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports/junitxml")

// Write test results to console.
//
// Tip: If you need to troubleshoot test runs, it helps to use the following reporting setup for ScalaTest.
//      Notably these suggested settings will ensure that all test output is written sequentially so that it is easier
//      to understand sequences of events, particularly cause and effect.
//      (cf. http://www.scalatest.org/user_guide/using_the_runner, section "Configuring reporters")
//
//        testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oUDT", "-eUDT")
//
//        // This variant also disables ANSI color output in the terminal, which is helpful if you want to capture the
//        // test output to file and then run grep/awk/sed/etc. on it.
//        testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oWUDT", "-eWUDT")
//
Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-o")
