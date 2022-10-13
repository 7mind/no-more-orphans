import mylib._
import org.scalatest.exceptions.TestFailedException
import org.scalatest.wordspec.AnyWordSpec

import scala.language.implicitConversions

class NoCatsMyIntSetTest extends AnyWordSpec {

  "can't refer to optional integrations" in {
    val t1 = intercept[TestFailedException](assertCompiles("MyIntSet.optionalCatsBoundedSemilatticeForMyIntSet")) // inaccessible
    assert(t1.getMessage.contains("implicit") || t1.getMessage.contains("given"))
  }

  "non-optional summons for MyIntSet" in {
    implicitly[scala.math.Ordering[MyIntSet]] // non-optional summon from MyIntSet object works
    implicitly[MyIntSet =:= MyIntSet] // non-optional summon from MyIntSet object works
    assertTypeError("implicitly[TC[MyIntSet]]")
  }
}
