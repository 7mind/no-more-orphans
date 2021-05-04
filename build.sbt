name := "no-more-orphans-test"

version := "0.1"

val dottyVersion = "3.0.0-RC3"

scalaVersion in Global := dottyVersion
crossScalaVersions in Global := Seq(/*"2.11.12",*/ "2.12.13", "2.13.5", dottyVersion)

val cats_effect = "org.typelevel" %% "cats-effect" % "2.5.0"
//val scalaz_core = "org.scalaz" %% "scalaz-core" % "7.2.27"
val scalatest = "org.scalatest" %% "scalatest" % "3.2.8"

scalacOptions in Global ++= Seq("-Xlog-implicits", "-language:higherKinds")

lazy val mylib = project.in(file("mylib"))
  .settings(
    name := "mylib",
    libraryDependencies ++=
      Seq(
        cats_effect % Optional,
//        scalaz_core % Optional
      )
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
    libraryDependencies ++= Seq(cats_effect, /*scalaz_core,*/ scalatest % Test)
  )
  .dependsOn(mylib)
