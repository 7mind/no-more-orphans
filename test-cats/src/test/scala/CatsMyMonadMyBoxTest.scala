import cats.data.{Chain, NonEmptyList}
import cats.implicits._
import cats.{Invariant, SemigroupK, Semigroupal}
import mylib._
import org.scalatest.WordSpec

class CatsMyMonadMyBoxTest extends WordSpec {

  "cats.Functor instance for MyBox works" in {
    assert(MyBox(5).map(_ + 5) == MyBox(10))
  }

  "There is no cats.Defer for MyBox" in {
    assertTypeError("implicitly[cats.Defer[MyBox]]")
  }

  "MyMonad for MyBox" in {
    assert(MyMonad[MyBox].pure(5) == MyBox(5))
  }

  "Semigroupal/SemigroupK/Invariant for MyBox" in {
    Semigroupal[MyBox]
    SemigroupK[MyBox]
    implicitly[Invariant[MyBox] with Semigroupal[MyBox]]
    implicitly[SemigroupK[MyBox] with Semigroupal[MyBox]]
    implicitly[SemigroupK[MyBox] with Invariant[MyBox] with Semigroupal[MyBox]]
    Invariant[MyBox]
  }

  "MyMonad from cats.Monad" in {
    assert(MyMonad[Option].pure(5) contains 5)
  }

  "MyMonad for NonEmptyList" in {
    assert(MyMonad[cats.data.Chain].getClass eq MyMonad.optionalMyMonadForCatsChain.getClass)
    assert(MyMonad[Chain].pure(5) == Chain(5))
    assert(MyMonad[NonEmptyList].pure(5) == NonEmptyList.of(5))
  }
}
