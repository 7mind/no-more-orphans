import cats.data.NonEmptyList
import cats.{Invariant, SemigroupK, Semigroupal}
import cats.implicits._
import mylib._
import org.scalactic.source.Position
import org.scalatest.wordspec.AnyWordSpec

import scala.language.implicitConversions

class CatsMyMonadMyBoxTest extends AnyWordSpec {
  implicit val Pos: Position = Position("", "", 1)

  "cats.Functor instance for MyBox works" in {
    assertResult(MyBox(5).map(_ + 5), "")(MyBox(10))
  }

  "There is no cats.Defer for MyBox" in {
    implicitly[scala.util.NotGiven[cats.Defer[MyBox]]]
  }

  "MyMonad for MyBox" in {
    assertResult(MyMonad[MyBox].pure(5), "")(MyBox(5))
  }

  "Semigroupal/SemigroupK/Invariant for MyBox" in {
    Semigroupal[MyBox]
    SemigroupK[MyBox]
    implicitly[Invariant[MyBox] & Semigroupal[MyBox]]
    implicitly[SemigroupK[MyBox] & Semigroupal[MyBox]]
    implicitly[SemigroupK[MyBox] & Invariant[MyBox] & Semigroupal[MyBox]]
    Invariant[MyBox]
  }

  "MyMonad from cats.Monad" in {
    assertResult(MyMonad[Option].pure(5).get, "")(5)
  }

  "MyMonad for NonEmptyList" in {
    assertResult(MyMonad[NonEmptyList].pure(5), "")(NonEmptyList.of(5))
  }
}
