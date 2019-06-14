name := "no-more-orphans-test"

version := "0.1"

scalaVersion := "2.13.0-M5"

crossScalaVersions := Seq("2.11.12", "2.12.8", "2.13.0-M5")

val cats_effect = "org.typelevel" %% "cats-effect" % "1.2.0"
val scalaz_core = "org.scalaz" %% "scalaz-core" % "7.2.27"
val scalatest = "org.scalatest" %% "scalatest" % "3.0.8"

scalacOptions in Global ++= Seq("-Xlog-implicits", "-language:higherKinds")

lazy val mylib = project.in(file("mylib"))
  .settings(
    name := "mylib",
    libraryDependencies += cats_effect % Optional,
    libraryDependencies += scalaz_core % Optional
  )


lazy val testNoCats = project.in(file("test-no-cats"))
  .settings(
    name := "test-no-cats",
    libraryDependencies += scalatest % Test
  )
  .dependsOn(mylib)

lazy val testCats = project.in(file("test-cats"))
  .settings(
    name := "test-cats",
    libraryDependencies ++= Seq(cats_effect, scalaz_core, scalatest % Test)
  )
  .dependsOn(mylib)

