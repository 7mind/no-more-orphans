import cats.kernel.{BoundedSemilattice, CommutativeMonoid, Monoid}
import mylib._
import org.scalatest.wordspec.AnyWordSpec

import scala.language.implicitConversions

class CatsMyIntSetTest extends AnyWordSpec {

  "Monoid/CommutativeMonoid/BoundedSemilattice for MyIntSet" in {
    implicitly[BoundedSemilattice[MyIntSet]]
    implicitly[CommutativeMonoid[MyIntSet]]
    implicitly[Monoid[MyIntSet]]
  }

  "Monoid Syntax works for MyIntSet" in {
    import cats.syntax.all._

    assert((MyIntSet(Set(1)) |+| MyIntSet(Set(2))) == MyIntSet(Set(1, 2)))
  }
  "Ordering for MyIntSet" in {
    implicitly[scala.math.Ordering[MyIntSet]]
  }
}
