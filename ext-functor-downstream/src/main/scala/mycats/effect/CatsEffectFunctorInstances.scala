package mycats.effect

import mycats.Functor

trait Sync[F[_]] extends Functor[F]

trait CatsEffectFunctorInstances
object CatsEffectFunctorInstances {
  implicit val syncList: Sync[List] = new Sync[List] {}
}
