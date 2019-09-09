package mycats

import mycats.effect.CatsEffectFunctorInstances
import mycats.mtl.CatsMtlFunctorInstances

trait Extensions[Ext]

trait Functor[F[_]] extends Extensions[(
  CatsEffectFunctorInstances,
  CatsMtlFunctorInstances,
)]
