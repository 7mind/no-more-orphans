package mylib

import cats.data.NonEmptyList

trait MyMonad[F[_]] {
  def pure[A](a: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object MyMonad extends LowPriorityMyMonad {
  def apply[F[_] : MyMonad]: MyMonad[F] = implicitly

  implicit val myMonadForList: MyMonad[List] = new MyMonad[List] {
    def pure[A](a: A): List[A] = List(a)
    def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
  }

  /**
    * `[F[_]: CatsNel]` guard is only required for 2.11, all other versions – 2.12, 2.13, dotty – accept unguarded
    * {{{
    * implicit val optionalMyMonadForCatsNonEmptyList: MyMonad[NonEmptyList]
    * }}}
    *  */
  implicit def optionalMyMonadForCatsNonEmptyList[F[_]: CatsNel]: MyMonad[NonEmptyList] = new MyMonad[NonEmptyList] {
    override def pure[A](a: A): NonEmptyList[A] = NonEmptyList.of(a)
    override def flatMap[A, B](fa: NonEmptyList[A])(f: A => NonEmptyList[B]): NonEmptyList[B] = fa.flatMap(f)
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

private sealed trait CatsNel[F[_]]
private object CatsNel {
  implicit val get: CatsNel[NonEmptyList] = null
}
