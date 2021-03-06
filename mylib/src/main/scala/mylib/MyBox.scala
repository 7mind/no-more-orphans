package mylib

final case class MyBox[A](get: A)

object MyBox extends LowPriorityMyBox {

  implicit val myMonadForMyBox: MyMonad[MyBox] = new MyMonad[MyBox] {
    override def pure[A](a: A): MyBox[A] = MyBox(a)

    override def flatMap[A, B](fa: MyBox[A])(f: A => MyBox[B]): MyBox[B] = f(fa.get)
  }

  implicit def optionalCatsFunctorForMyBox[F[_[_]]: CatsFunctor]: F[MyBox] = {
    new cats.Functor[MyBox] {
      def map[A, B](fa: MyBox[A])(f: A => B): MyBox[B] =
        MyBox(f(fa.get))
    }.asInstanceOf[F[MyBox]]
  }

}

trait LowPriorityMyBox {

  type Fixup[A] = A

  implicit def optionalCatsSemigroupalSemigroupKInvariantForMyBox[F[_[_]]: CatsSemigroupalSemigroupKInvariant]: Fixup[F[MyBox]] = {
    new ImpllSemigroupalSemigroupKInvariant[MyBox] {
      def combineK[A](x: MyBox[A], y: MyBox[A]): MyBox[A] = y
      def product[A, B](fa: MyBox[A], fb: MyBox[B]): MyBox[(A, B)] = MyBox((fa.get, fb.get))
      def imap[A, B](fa: MyBox[A])(f: A => B)(g: B => A): MyBox[B] = MyBox(f(fa.get))
    }.asInstanceOf[F[MyBox]]
  }

}

private final abstract class CatsFunctor[F[_[_]]]
private object CatsFunctor {
  @inline implicit final def get: CatsFunctor[cats.Functor] = null
}

private final abstract class CatsIsAvailable
private object CatsIsAvailable {
  implicit def get[F[_[_]]: CatsFunctor]: CatsIsAvailable = null
}

private final abstract class CatsSemigroupalSemigroupKInvariant[F[_[_]]]
private object CatsSemigroupalSemigroupKInvariant {
  implicit def get(implicit haveCats: CatsIsAvailable): CatsSemigroupalSemigroupKInvariant[ImpllSemigroupalSemigroupKInvariant] = null
}

private trait ImpllSemigroupalSemigroupKInvariant[K[_]] extends cats.Semigroupal[K] with cats.SemigroupK[K] with cats.Invariant[K]
