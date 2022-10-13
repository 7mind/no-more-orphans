package mylib.lib

import scala.{specialized => sp}

trait SemigroupX[@sp(Int, Long, Float, Double) A] extends Any with Serializable { self =>}
trait MonoidX[@sp(Int, Long, Float, Double) A] extends Any with SemigroupX[A] { self =>
  def empty: A
  def combine(x: A, y: A): A
}
trait CommutativeMonoidX[@sp(Int, Long, Float, Double) A] extends Any with MonoidX[A] { self =>}
trait BoundedSemilatticeX[@sp(Int, Long, Float, Double) A] extends Any with CommutativeMonoidX[A] { self =>}
