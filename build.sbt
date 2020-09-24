name := "no-more-orphans-test"

ThisBuild / version := "0.1-SNAPSHOT"

val dottyVersion = "0.19.0-RC1"
//val dottyVersion = dottyLatestNightlyBuild().get

scalaVersion in Global := crossScalaVersions.value.head
//crossScalaVersions in Global := Seq("2.11.12", "2.12.10", "2.13.1", dottyVersion)
//crossScalaVersions in Global := Seq("2.12.10", "2.11.12", "2.13.1", dottyVersion)
crossScalaVersions in Global := Seq(dottyVersion, "2.12.10", "2.11.12", "2.13.1")

val cats_effect = "org.typelevel" %% "cats-effect" % "2.0.0-RC1"
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

lazy val extFunctorFictional = project.in(file("ext-functor-fictional"))
  .settings(name := "ext-functor-fictional")
  .settings(version := "0.0-SNAPSHOT")

lazy val extFunctor = project.in(file("ext-functor"))
//  .dependsOn(extFunctorFictional % "plugin->default(compile)")
//  .dependsOn(extFunctorFictional % Compile)
  .dependsOn(extFunctorFictional % Optional)
//  .dependsOn(extFunctorFictional)
//  .settings(libraryDependencies += "ext-functor-downstream" %% "ext-functor-downstream" % "0.0-SNAPSHOT")

lazy val extFunctorDownstream = project.in(file("ext-functor-downstream"))
  .settings(name := "ext-functor-downstream")
//  .dependsOn(extFunctor)
  .settings(libraryDependencies += "extfunctor" %% "extfunctor" % "0.1-SNAPSHOT")
  .settings(
    libraryDependencies += scalatest % Test withDottyCompat scalaVersion.value,
  )

lazy val extFunctorNoDownstream = project.in(file("ext-functor-no-downstream"))
  .dependsOn(extFunctor)
  .settings(
    libraryDependencies += scalatest % Test withDottyCompat scalaVersion.value,
  )
