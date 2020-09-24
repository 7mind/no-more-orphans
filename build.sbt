name := "no-more-orphans-test"

version := "0.1"

scalaVersion  in ThisBuild := "2.13.3"

//crossScalaVersions in ThisBuild := Seq("2.11.12", "2.12.12", "2.13.3") // not all features work on 2.11 (MyMonad.optionalMyMonadForCatsChain)
crossScalaVersions in ThisBuild := Seq( "2.12.12", "2.13.3")

val cats_effect = "org.typelevel" %% "cats-effect" % "2.0.0"
val scalaz_core = "org.scalaz" %% "scalaz-core" % "7.2.27"
val scalatest = "org.scalatest" %% "scalatest" % "3.0.8"

//scalacOptions in Global ++= Seq("-Xlog-implicits", "-language:higherKinds")

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

