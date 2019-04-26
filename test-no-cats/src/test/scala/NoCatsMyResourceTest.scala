
import mylib.MyResource
import org.scalactic.source.Position
import org.scalatest.WordSpec

import scala.util.Try

import scala.language.implicitConversions

class NoCatsMyResourceTest extends WordSpec {
  implicit val Pos: Position = Position("", "", 1)

  "non-cats methods are accessible, but cats syntax isn't" in {
    val resource = MyResource.make(Try(1))(_ => Try(()))

    resource.acquire
//    resource.toCats // inaccessible
  }

}

//Error:(12, 5) Symbol 'type cats.effect.Resource' is missing from the classpath.
//This symbol is required by 'value mylib.MyResource.catsResource'.
//Make sure that type Resource is in your classpath and check for conflicting dependencies with `-Ylog-classpath`.
//A full rebuild may help if 'MyResource.class' was compiled against an incompatible version of cats.effect.
//    resource.toCats
