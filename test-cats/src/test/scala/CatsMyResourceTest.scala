
import cats.effect.Resource
import mylib.MyResource
import org.scalatest.WordSpec

import scala.util.Try
import cats.implicits._

class CatsMyResourceTest extends WordSpec {

  "cats-specific syntax is automatically accessible" in {
    val resource = MyResource.make(Try(1))(_ => Try(()))

    val catsResource: Resource[Try, Int] = resource.toCats
    val convertedResource: MyResource[Try, Int] = catsResource
  }

}

//Error:(12, 5) Symbol 'type cats.effect.Resource' is missing from the classpath.
//This symbol is required by 'value mylib.MyResource.catsResource'.
//Make sure that type Resource is in your classpath and check for conflicting dependencies with `-Ylog-classpath`.
//A full rebuild may help if 'MyResource.class' was compiled against an incompatible version of cats.effect.
//    resource.toCats
