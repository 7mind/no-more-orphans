package mylib

trait MyMonad[F[_]] {
  def pure[A](a: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object MyMonad extends LowPriorityMyMonad {
  def apply[F[_] : MyMonad]: MyMonad[F] = implicitly

  implicit def myMonadForList: MyMonad[List] = new MyMonad[List] {
    def pure[A](a: A): List[A] = List(a)

    def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
  }
}

trait LowPriorityMyMonad {
  implicit def optionalMyMonadFromCatsMonad[F[_], M[_[_]]: CatsMonad](implicit m: M[F]): MyMonad[F] = {
    val M = m.asInstanceOf[cats.Monad[F]]
    new MyMonad[F] {
      override def pure[A](a: A): F[A] = M.pure(a)
      override def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = M.flatMap(fa)(f)
    }
  }

  implicit def optionalMyMonadFromScalazMonad[F[_], M[_[_]]: ScalazMonad](implicit m: M[F]): MyMonad[F] = {
    val M = m.asInstanceOf[scalaz.Monad[F]]
    new MyMonad[F] {
      override def pure[A](a: A): F[A] = M.pure(a)
      override def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = M.bind(fa)(f)
    }
  }
}

private sealed trait CatsMonad[M[_[_]]]
private object CatsMonad {
  implicit val get: CatsMonad[cats.Monad] = null
}

private sealed trait ScalazMonad[M[_[_]]]
private object ScalazMonad {
  implicit val get: CatsMonad[scalaz.Monad] = null
}
