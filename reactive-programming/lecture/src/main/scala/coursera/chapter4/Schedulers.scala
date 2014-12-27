package coursera.chapter4

import rx.lang.scala.Observable

object Schedulers {

  def from[T](seq: Iterable[T]): Observable[T] = ???

  def nats(): Iterable[Int] = new Iterable[Int] {
    var i = - 1
    def iterator: Iterator[Int] = new Iterator[Int] {
      def hasNext: Boolean = { true }
      def next(): Int = { i += 1; i }
    }
  }

  val infinite = nats()
  val s = from(infinite).subscribe(x => println(x))
}
