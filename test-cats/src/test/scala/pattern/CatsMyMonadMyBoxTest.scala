package pattern

import cats.implicits._
import mylib.pattern._
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

  "MyMonad from cats.Monad" in {
    assert(MyMonad[Option].pure(5) contains 5)
  }
}
