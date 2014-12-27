package coursera.chapter4

import org.scalatest._
import rx.lang.scala.Observable
import rx.lang.scala.subscriptions._
import rx.lang.scala.Subscription

class SubscriptionsTest extends FlatSpec with Matchers {

  it should "" in  {
    val s = Subscription {
      println("bye, bye")
    }

    s.unsubscribe()
  }
}
