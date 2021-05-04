package test

import mycats.Functor
import mycats.effect.Sync
import org.scalactic.source.Position
import org.scalatest.wordspec.AnyWordSpec

import scala.Console.in

class TestSummon extends AnyWordSpec {
  def the[A <: AnyRef](implicit a: A): a.type = a

  "summon" in {
    val isSync: Sync[List] = the[Functor[List]]
    println(isSync)
  }

  // dotty
  implicit def Pos: Position = Position("", "", 1)
}
