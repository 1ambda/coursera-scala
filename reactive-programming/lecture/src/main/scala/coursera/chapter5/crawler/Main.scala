package coursera.chapter5.crawler

import akka.actor.{Actor, Props, ReceiveTimeout}
import scala.concurrent.duration._

class Main extends Actor {
  import Receptionist._
  import context.dispatcher

  val receptionist = context.actorOf(Props[Receptionist], "receptionist")

  receptionist ! Get("http://www.google.com")

  context.system.scheduler.scheduleOnce(10 seconds, self, ReceiveTimeout)

  def receive = {
    case Result(url, links) =>
      println(links.toVector.sorted.mkString(s"Results for '$url':\n", "\n", "\n"))
    case Failed(url) =>
      println(s"Failed to fetch '$url'\n")
    case ReceiveTimeout =>
      context.stop(self)
  }

  override def postStop(): Unit = {
    WebClient.shutdown()
  }
}
