package mycats

trait Extensions[Ext]
//
//trait Functor[F[_]] extends Extensions[
//  ( CatsEffectFunctorInstances, CatsMtlFunctorInstances )
//]
//
//object Functor {
//  implicit val functorOption: Functor[Option] = new Functor[Option] {}
//}

given xa (given dummyImplicit: DummyImplicit) {
  def (a: Any) pure: Unit = a
}

given xa (given dummyImplicit: DummyImplicit, dummyImplicit: DummyImplicit) {
  def (a: Any) map (): Unit = a
}

trait Functor[F[+ _]]

//trait Contra[F[-_]]
//
//type Given[F[_[_]], G[_[_]], H[_]] = (F[H], G[H]) => H[Unit]

//case class Id[A](a: A)

//type Id[A] =A

//def x[A]: Given[Functor, Contra, [S] =>> A] = ???

//def x2: (Functor[Id], Contra[Id]) => Id[Unit] = ???
