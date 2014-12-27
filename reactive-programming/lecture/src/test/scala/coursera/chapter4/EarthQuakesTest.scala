package coursera.chapter4

import org.scalatest._
import rx.lang.scala.Observable

class EarthQuakesTest extends FlatSpec with Matchers {

  it should "" in  {
    val first = Observable.just(10, 11, 12)
    val second = Observable.just(10, 11, 12)
    val booleans = for ((n1, n2) <- (first zip second)) yield (n1 == n2)

    booleans.foreach { println(_) }
  }
}
