package mylib.withtypes

import mylib.MyBox

object FailedAttempts {

  object Aux {

    trait LowPriorityMyBox {
      implicit def optionalCatsSemigroupalSemigroupKInvariantForMyBox[F[_[_]]](implicit ev: CatsSemigroupalSemigroupKInvariant {type Tc[G[_]] <: F[G]}): F[MyBox] with F[MyBox] = {
        new cats.Semigroupal[MyBox] with cats.SemigroupK[MyBox] with cats.Invariant[MyBox] {
          def combineK[A](x: MyBox[A], y: MyBox[A]): MyBox[A] = y

          def product[A, B](fa: MyBox[A], fb: MyBox[B]): MyBox[(A, B)] = MyBox((fa.get, fb.get))

          def imap[A, B](fa: MyBox[A])(f: A => B)(g: B => A): MyBox[B] = MyBox(f(fa.get))
        }.asInstanceOf
      }
    }

    private final abstract class CatsSemigroupalSemigroupKInvariant {
      type Tc[F[_]]
    }
    private object CatsSemigroupalSemigroupKInvariant {
      // Symbol 'type cats.SemigroupK' is missing
      @inline implicit final def get: CatsSemigroupalSemigroupKInvariant {type Tc[F[_]] = cats.SemigroupK[F] with cats.Invariant[F] with cats.Semigroupal[F]} = null
    }

  }

}
