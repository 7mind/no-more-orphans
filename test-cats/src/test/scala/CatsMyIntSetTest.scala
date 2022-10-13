import mylib._
import mylib.lib._
import org.scalatest.wordspec.AnyWordSpec

import scala.language.implicitConversions

class CatsMyIntSetTest extends AnyWordSpec {

  "Monoid/CommutativeMonoid/BoundedSemilattice for MyIntSet" in {
    implicitly[BoundedSemilatticeX[MyIntSet]]
    implicitly[CommutativeMonoidX[MyIntSet]]
    implicitly[MonoidX[MyIntSet]]
  }

  "Ordering for MyIntSet" in {
    implicitly[scala.math.Ordering[MyIntSet]]
  }
}
