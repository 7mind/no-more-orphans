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

    private[SuccessfulAttempts] sealed trait CatsSemigroupalSemigroupKInvariant[F[_[_]]]
    private[SuccessfulAttempts] object CatsSemigroupalSemigroupKInvariant {
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

    private[SuccessfulAttempts] sealed trait CatsSemigroupalSemigroupKInvariant[F[_[_]]]
    private[SuccessfulAttempts] object CatsSemigroupalSemigroupKInvariant {
      type SemigroupalSemigroupKInvariant[F[_]] <: cats.Semigroupal[F] with cats.SemigroupK[F] with cats.Invariant[F]
      implicit def get(implicit guard: CatsIsAvailable): CatsSemigroupalSemigroupKInvariant[SemigroupalSemigroupKInvariant] = null
    }

  }

  private[SuccessfulAttempts] sealed trait CatsSemigroupal[F[_[_]]]
  private[SuccessfulAttempts] object CatsSemigroupal {
    implicit val get: CatsSemigroupal[cats.Semigroupal] = null
  }

  private[SuccessfulAttempts] sealed trait CatsSemigroupK[F[_[_]]]
  private[SuccessfulAttempts] object CatsSemigroupK {
    implicit val get: CatsSemigroupK[cats.SemigroupK] = null
  }

  private[SuccessfulAttempts] sealed trait CatsInvariant[F[_[_]]]
  private[SuccessfulAttempts] object CatsInvariant {
    implicit val get: CatsInvariant[cats.Invariant] = null
  }

  private[SuccessfulAttempts] sealed trait CatsIsAvailable
  private[SuccessfulAttempts] object CatsIsLoaded {
    implicit def get[F[_[_]]: CatsFunctor]: CatsIsAvailable = null
  }
}
