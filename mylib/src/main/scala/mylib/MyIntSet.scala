package mylib

import mylib.lib._

import scala.math.Ordering

final case class MyIntSet(i: Set[Int])

object MyIntSet {

  implicit def optionalCatsBoundedSemilatticeForMyIntSet[F[_]: CatsBoundedSemilattice]: F[MyIntSet] = {
    new BoundedSemilatticeX[MyIntSet] {
      override def empty: MyIntSet = MyIntSet(Set.empty)
      override def combine(x: MyIntSet, y: MyIntSet): MyIntSet = MyIntSet(x.i ++ y.i)
    }.asInstanceOf[F[MyIntSet]]
  }

  implicit val orderingMyIntSet: Ordering[MyIntSet] = null.asInstanceOf[Ordering[MyIntSet]]

}

private sealed trait CatsBoundedSemilattice[F[_]]
private object CatsBoundedSemilattice {
  // FIXME this misbehaves on Scala 3: generates false implicit-not-found errors when implicit search is in another module
  implicit val get: CatsBoundedSemilattice[BoundedSemilatticeX] = null

  // these workarounds all work to pacify Scala 3
//  implicit def get[T[x] >: cats.kernel.BoundedSemilattice[x]]: CatsBoundedSemilattice[T] = null

//  implicit val get: CatsBoundedSemilattice[[a] =>> cats.kernel.BoundedSemilattice[a]] = null

//  implicit val get: CatsBoundedSemilattice[ImpllSemigroupalSemigroupKInvariant] = null
//  private[mylib] type ImpllSemigroupalSemigroupKInvariant[K] = cats.kernel.BoundedSemilattice[K]
  //
}
