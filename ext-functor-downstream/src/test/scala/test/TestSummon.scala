package test

import mycats.Functor
import mycats.effect.{CatsEffectFunctorInstances, Sync}
import mycats.mtl.CatsMtlFunctorInstances
import org.scalactic.source.Position
import org.scalatest.WordSpec

class TestSummon extends WordSpec {
  def the[A <: AnyRef](implicit a: A): a.type = a

  "summon downstream instances" in {
    implicitly[Functor[List]]
    implicitly[Sync[List]]
    val isSync: Sync[List] = the[Functor[List]]
    println(isSync)
//    println(scala.reflect.runtime.universe.typeOf[CatsEffectFunctorInstances].companion)
//    println(scala.reflect.runtime.universe.typeOf[CatsMtlFunctorInstances].companion)
  }

  // dotty
  implicit def Pos: Position = Position("", "", 1)
}
