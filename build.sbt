name := "no-more-orphans-test"

version := "0.1"

val dottyVersion = "3.0.0-RC3"

scalaVersion in Global := dottyVersion
crossScalaVersions in Global := Seq(dottyVersion)

val cats_effect = "org.typelevel" %% "cats-effect" % "2.5.0"
val scalatest = "org.scalatest" %% "scalatest" % "3.2.8"

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
    libraryDependencies ++= Seq(cats_effect, /*scalaz_core, */scalatest % Test)
  )
  .dependsOn(mylib)

lazy val extFunctorFictional = project.in(file("ext-functor-fictional"))
  .settings(skip in publish := true)

lazy val extFunctor = project.in(file("ext-functor"))
  .dependsOn(extFunctorFictional % Optional)

lazy val extFunctorDownstream = project.in(file("ext-functor-downstream"))
  .dependsOn(extFunctor)
  .settings(
    libraryDependencies += scalatest % Test,
  )

lazy val extFunctorNoDownstream = project.in(file("ext-functor-no-downstream"))
  .dependsOn(extFunctor)
  .settings(
    libraryDependencies += scalatest % Test,
  )
