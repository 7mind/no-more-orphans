package mylib.pattern

trait GetTc[Name <: TcHolder, ResultTc[A]] {
  implicit def equiv[A]: ResultTc[A] =:= Name#Tc[A]

  object reverse {
    implicit final def eqReverse[A]: Name#Tc[A] =:= ResultTc[A] = {
      equiv[A].asInstanceOf[Name#Tc[A] =:= ResultTc[A]]
    }
  }
}

trait GetTc1[Name <: TcHolder1, ResultTc[F[_]]] {
  implicit def equiv[F[_]]: ResultTc[F] =:= Name#Tc[F]

  object reverse {
    implicit final def eqReverse[F[_]]: Name#Tc[F] =:= ResultTc[F] = {
      equiv[F].asInstanceOf[Name#Tc[F] =:= ResultTc[F]]
    }
  }
}

trait TcHolder {
  type Tc[_]
}

trait TcHolder1 {
  type Tc[_[_]]
}

sealed trait CatsFunctor extends TcHolder1 {
  override type Tc[F[_]] = cats.Functor[F]
}

object CatsFunctor {
  implicit val get: GetTc1[CatsFunctor, cats.Functor] = {
    new GetTc1[CatsFunctor, cats.Functor] {
      override def equiv[F[_]] = implicitly
    }
  }
}

sealed trait CatsMonadTc extends TcHolder1 {
  type Tc[F[_]] = cats.Monad[F]
}

object CatsMonadTc {
  implicit val get: GetTc1[CatsMonadTc, cats.Monad] = new GetTc1[CatsMonadTc, cats.Monad] {
    override def equiv[F[_]] = implicitly
  }
}

final case class MyBox[A](get: A)

object MyBox {

  implicit val myMonadForMyBox: MyMonad[MyBox] = new MyMonad[MyBox] {
    override def pure[A](a: A): MyBox[A] = MyBox(a)

    override def flatMap[A, B](fa: MyBox[A])(f: A => MyBox[B]): MyBox[B] = f(fa.get)
  }

  implicit def optionalCatsFunctorForMyBox[F[_[_]]](implicit ev: GetTc1[CatsFunctor, F]): F[MyBox] = {
    import ev.reverse._

    new cats.Functor[MyBox] {
      def map[A, B](fa: MyBox[A])(f: A => B): MyBox[B] =
        MyBox(f(fa.get))
    }
  }

}

trait MyMonad[F[_]] {
  def pure[A](a: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object MyMonad {
  def apply[F[_]: MyMonad]: MyMonad[F] = implicitly

  implicit def myMonadForList: MyMonad[List] = new MyMonad[List] {
    def pure[A](a: A): List[A] = List(a)
    def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
  }

  implicit def optionalMyMonadFromCatsMonad[F[_], M[_[_]]](implicit ev: GetTc1[CatsMonadTc, M], F: M[F]): MyMonad[F] = {
    import ev._

    new MyMonad[F] {
      override def pure[A](a: A): F[A] = F.pure(a)
      override def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = F.flatMap(fa)(f)
    }
  }

}
