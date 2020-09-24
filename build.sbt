name := "no-more-orphans-test"

version := "0.1"

val dottyVersion = "0.27.0-RC1"
//val dottyVersion = dottyLatestNightlyBuild().get

scalaVersion in Global := dottyVersion
crossScalaVersions in Global := Seq("2.12.12", "2.13.3", dottyVersion)

val cats_effect = "org.typelevel" %% "cats-effect" % "2.0.0"
val scalaz_core = "org.scalaz" %% "scalaz-core" % "7.2.27"
val scalatest = "org.scalatest" %% "scalatest" % "3.0.8"

scalacOptions in Global ++= Seq("-Xlog-implicits", "-language:higherKinds")

lazy val mylib = project.in(file("mylib"))
  .settings(
    name := "mylib",
    libraryDependencies ++=
      Seq(
        cats_effect % Optional,
        scalaz_core % Optional
      ).map(_.withDottyCompat(scalaVersion.value)
    )
  )

lazy val testNoCats = project.in(file("test-no-cats"))
  .settings(
    name := "test-no-cats",
    libraryDependencies += scalatest % Test withDottyCompat scalaVersion.value
  )
  .dependsOn(mylib)

lazy val testCats = project.in(file("test-cats"))
  .settings(
    name := "test-cats",
    libraryDependencies ++= Seq(cats_effect, scalaz_core, scalatest % Test).map(_.withDottyCompat(scalaVersion.value))
  )
  .dependsOn(mylib)

