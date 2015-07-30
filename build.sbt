name := "ScalaFXMLKeyBinds"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx"        % "8.0.40-R8",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.2.2",
  "com.1stleg" % "jnativehook" % "2.0.2",
  "org.scala-lang" % "scala-reflect" % "2.11.7"

)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full)

fork := true