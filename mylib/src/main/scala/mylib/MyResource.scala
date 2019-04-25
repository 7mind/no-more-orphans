package mylib

import scala.language.implicitConversions

trait MyResource[F[_], A] {
  def acquire: F[A]
  def release(a: A): F[Unit]
}

// Users without a cats-effect dependency will be able to call `make` and any other methods,
// but won't see the implicit conversions.
// Users with cats-effect will get implicit syntax automatically without imports.
object MyResource {
  def make[F[_], A](acquire0: F[A])(release0: A => F[Unit]): MyResource[F, A] = new MyResource[F, A] {
    override def acquire: F[A] = acquire0
    override def release(a: A): F[Unit] = release0(a)
  }

  // can define non-orphan extension methods
  implicit class ToCats[F[_], A](myResource: MyResource[F, A]) {
    def toCats(implicit F: cats.Functor[F]): cats.effect.Resource[F, A] = {
      cats.effect.Resource.make(myResource.acquire)(myResource.release)
    }
  }

  // can define non-orphan implicit conversions
  implicit def fromCats[F[_], A](catsResource: cats.effect.Resource[F, A]): MyResource[F, A] = null
}
