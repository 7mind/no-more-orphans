package test

import mycats.Functor
import org.scalactic.source.Position
import org.scalatest.WordSpec

class TestClassNonbroken extends WordSpec {
  "new Functor" in {
    println(new Functor[Option] {})
  }

  // dotty
  implicit def Pos: Position = Position("", "", 1)
}
