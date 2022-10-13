
import mylib._
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Try

import scala.language.implicitConversions

class NoCatsMyResourceTest extends AnyWordSpec {

  "non-cats methods are accessible, but cats syntax isn't" in {
    val resource = MyResource.make(Try(1))(_ => Try(()))

    resource.acquire
//    resource.toCats // inaccessible
  }

  "non-optional summons for MyResource" in {
    implicitly[MyResource[Option, Int] =:= MyResource[Option, Int]] // non-optional summon from MyIntSet object works
    assertTypeError("implicitly[TC[MyResource[Option, Int]]]")
  }
}

//Error:(12, 5) Symbol 'type cats.effect.Resource' is missing from the classpath.
//This symbol is required by 'value mylib.MyResource.catsResource'.
//Make sure that type Resource is in your classpath and check for conflicting dependencies with `-Ylog-classpath`.
//A full rebuild may help if 'MyResource.class' was compiled against an incompatible version of cats.effect.
//    resource.toCats
