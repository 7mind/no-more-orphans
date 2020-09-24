package mylib.withtypes

import mylib.{CatsFunctor, MyBox}

object SuccessfulAttempts {

  object CompositeReturn {

    trait LowPriorityMyBox {
      implicit def optionalCatsSemigroupalSemigroupKInvariantForMyBox[F[_[_]]: CatsSemigroupal, G[_[_]]: CatsSemigroupK, C[_[_]]: CatsInvariant]: F[MyBox] with G[MyBox] with C[MyBox] = {
        new cats.Semigroupal[MyBox] with cats.SemigroupK[MyBox] with cats.Invariant[MyBox] {
          def combineK[A](x: MyBox[A], y: MyBox[A]): MyBox[A] = y
          def product[A, B](fa: MyBox[A], fb: MyBox[B]): MyBox[(A, B)] = MyBox((fa.get, fb.get))
          def imap[A, B](fa: MyBox[A])(f: A => B)(g: B => A): MyBox[B] = MyBox(f(fa.get))
        }.asInstanceOf[F[MyBox] with G[MyBox] with C[MyBox]]
      }
    }

  }

  object SubtypeCompositeTypeLambda {

    trait LowPriorityMyBox {
      implicit def optionalCatsSemigroupalSemigroupKInvariantForMyBox[F[_[_]]: CatsSemigroupalSemigroupKInvariant]: F[MyBox] with F[MyBox] = {
        new cats.Semigroupal[MyBox] with cats.SemigroupK[MyBox] with cats.Invariant[MyBox] {
          def combineK[A](x: MyBox[A], y: MyBox[A]): MyBox[A] = y
          def product[A, B](fa: MyBox[A], fb: MyBox[B]): MyBox[(A, B)] = MyBox((fa.get, fb.get))
          def imap[A, B](fa: MyBox[A])(f: A => B)(g: B => A): MyBox[B] = MyBox(f(fa.get))
        }.asInstanceOf[F[MyBox]]
      }
    }

    private final abstract class CatsSemigroupalSemigroupKInvariant[F[_[_]]]
    private object CatsSemigroupalSemigroupKInvariant {
      // Note the `<:` instead of `=`, it's important!
      implicit def get[F[_[_]]: CatsSemigroupal, G[_[_]]: CatsSemigroupK, C[_[_]]: CatsInvariant]: CatsSemigroupalSemigroupKInvariant[({ type l[K[_]] <: F[K] with G[K] with C[K] })#l] = null
    }

  }

  // this is ok, but Intellij fails at highlighting that. When we use a trait instead of typealias, we're fine though
  object SubtypeGuardedTypeAlias {

    trait LowPriorityMyBox {
      implicit def optionalCatsSemigroupalSemigroupKInvariantForMyBox[F[_[_]]: CatsSemigroupalSemigroupKInvariant]: F[MyBox] with F[MyBox] = {
        new cats.Semigroupal[MyBox] with cats.SemigroupK[MyBox] with cats.Invariant[MyBox] {
          def combineK[A](x: MyBox[A], y: MyBox[A]): MyBox[A] = y
          def product[A, B](fa: MyBox[A], fb: MyBox[B]): MyBox[(A, B)] = MyBox((fa.get, fb.get))
          def imap[A, B](fa: MyBox[A])(f: A => B)(g: B => A): MyBox[B] = MyBox(f(fa.get))
        }.asInstanceOf[F[MyBox]]
      }
    }

    private final abstract class CatsSemigroupalSemigroupKInvariant[F[_[_]]]
    private object CatsSemigroupalSemigroupKInvariant {
      type SemigroupalSemigroupKInvariant[F[_]] <: cats.Semigroupal[F] with cats.SemigroupK[F] with cats.Invariant[F]
      implicit def get(implicit guard: CatsIsAvailable): CatsSemigroupalSemigroupKInvariant[SemigroupalSemigroupKInvariant] = null
    }

  }

  private final abstract class CatsSemigroupal[F[_[_]]]
  private object CatsSemigroupal {
    @inline implicit final def get: CatsSemigroupal[cats.Semigroupal] = null
  }

  private final abstract class CatsSemigroupK[F[_[_]]]
  private object CatsSemigroupK {
    @inline implicit final def get: CatsSemigroupK[cats.SemigroupK] = null
  }

  private final abstract class CatsInvariant[F[_[_]]]
  private object CatsInvariant {
    @inline implicit final def get: CatsInvariant[cats.Invariant] = null
  }

  private final abstract class CatsIsAvailable
  private object CatsIsLoaded {
    implicit def get[F[_[_]]: CatsFunctor]: CatsIsAvailable = null
  }
}
