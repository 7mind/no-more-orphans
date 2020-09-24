package mylib

import cats.data.Chain

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

  /** Note: you _CAN_ include direct instances of your typeclasses for optional types as long as:
    *
    *  1. the foreign type must be directly a _CLASS_ or a _TRAIT_, it cannot be a type alias and you cannot use type lambas (kind projector syntax)
    *     {{{
    *       implicit def m: MyMonad[ForeignClass] // OK
    *
    *       implicit def m: MyMonad[ForeignTypeAlias] // NOT OK
    *       implicit def m: MyMonad[ForeignClassX[*, ?]] // NOT OK
    *       implicit def m(implicit M: cats.Monad[ForeignClass]): MyMonad[ForeignClass] // NOT OK
    *     }}}
    *  2. All implicit arguments must be either non-foreign or obscured by the 'no more orphans' trick
    *
    * But note that this only works for Scala 2.12+, 2.13+ & 3.0+, this doesn't work on Scala 2.11
    */
  implicit def optionalMyMonadForCatsChain: MyMonad[cats.data.Chain] = new MyMonad[cats.data.Chain] {
    override def pure[A](a: A): Chain[A] = Chain(a)
    override def flatMap[A, B](fa: Chain[A])(f: A => Chain[B]): Chain[B] = fa.flatMap(f)
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

private final abstract class CatsMonad[M[_[_]]]
private object CatsMonad {
  @inline implicit final def get: CatsMonad[cats.Monad] = null
}

private final abstract class ScalazMonad[M[_[_]]]
private object ScalazMonad {
  @inline implicit final def get: CatsMonad[scalaz.Monad] = null
}

private final abstract class CatsChain[F[_]]
private object CatsChain {
  @inline implicit final def get: CatsChain[cats.data.Chain] = null
}
