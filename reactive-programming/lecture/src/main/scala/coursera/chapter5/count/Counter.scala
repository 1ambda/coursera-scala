package coursera.chapter5.count

import akka.actor.Actor

class Counter extends Actor {
  var count = 0

  def receive = {
    case "incr" =>
      println("Counter received 'incr'")
      count += 1
    case "get" => sender ! count
  }
}

