package coursera.chapter5.count

import akka.actor.Actor
import akka.actor.Props

// sbt run-main akka.Main coursera.chapter5.count.Main
class Main extends Actor {

  val counter = context.actorOf(Props[Counter], "counter")

  counter ! "incr"
  counter ! "incr"
  counter ! "incr"
  counter ! "get"

  def receive = {
    case count: Int =>
      println(s"count was $count")
      context.stop(self)
  }
}
