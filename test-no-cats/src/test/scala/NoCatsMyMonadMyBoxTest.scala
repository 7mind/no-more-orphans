
import mylib.{MyBox, MyMonad}
import org.scalactic.source.Position
import org.scalatest.WordSpec

import scala.language.implicitConversions

final class NoCatsMyMonadMyBoxTest extends WordSpec {
  implicit val Pos: Position = Position("", "", 1)

  "can't refer to optional integrations" in {
//    MyBox.catsMonadForBox // inacessible
//    MyMonad.fromCatsMonad // inacessible
//    MyMonad.fromScalazMonad // inacessible
  }

  "non-cats summons work" in {
    implicitly[MyMonad[List]] // non-optional summon from MyMonad object works
    implicitly[MyMonad[MyBox]] // non-optional summon from MyBox object works
    implicitly[MyBox[Unit] =:= MyBox[Unit]] // non-optional summon from MyBox object works
//    implicitly[TestTC[MyBox]]
    // [error] 22 |    implicitly[TestTC[MyBox]]
    // [error]    |                             ^
    // [error]    |no implicit argument of type TestTC[mylib.MyBox] was found for parameter ev of method implicitly in object DottyPredef.
    // [error]    |I found:
    // [error]    |
    // [error]    |    mylib.MyBox.optionalCatsFunctorForMyBox[F](
    // [error]    |      /* missing */implicitly[mylib.CatsFunctor[TestTC]]
    // [error]    |    )
    // [error]    |
    // [error]    |But no implicit values were found that match type mylib.CatsFunctor[TestTC].
  }
}

trait TestTC[F[_]]

//Error:(7, 5) Symbol 'type cats.Monad' is missing from the classpath.
//This symbol is required by 'value mylib.MyBox.catsMonadForBox'.
//Make sure that type Monad is in your classpath and check for conflicting dependencies with `-Ylog-classpath`.
//A full rebuild may help if 'MyBox.class' was compiled against an incompatible version of cats.
//    MyBox.catsMonadForBox

//Error:(8, 5) Symbol 'term <root>.cats' is missing from the classpath.
//This symbol is required by 'value mylib.MyMonad.evidence$2'.
//Make sure that term cats is in your classpath and check for conflicting dependencies with `-Ylog-classpath`.
//A full rebuild may help if 'MyMonad.class' was compiled against an incompatible version of <root>.
//    MyMonad.fromCatsMonad

//Error:(9, 5) Symbol 'term <root>.scalaz' is missing from the classpath.
//This symbol is required by 'value mylib.MyMonad.evidence$3'.
//Make sure that term scalaz is in your classpath and check for conflicting dependencies with `-Ylog-classpath`.
//A full rebuild may help if 'MyMonad.class' was compiled against an incompatible version of <root>.
//    MyMonad.fromScalazMonad
