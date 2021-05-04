package test

import mycats.Functor
import org.scalactic.source.Position
import org.scalatest.wordspec.AnyWordSpec

class TestClassNonbroken extends AnyWordSpec {
  "summon functor" in {
    println(implicitly[Functor[Option]])
  }

  "new Functor" in {
    println(new Functor[Option] {})
  }

  // dotty
  implicit def Pos: Position = Position("", "", 1)
}
