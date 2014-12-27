package coursera.chapter5.crawler

import akka.actor.{Actor, Props, ActorRef, ReceiveTimeout, ActorLogging}
import scala.concurrent.duration._

object Controller {
  case class Check(link: String, depth: Int)
  case class Result(links: Set[String])
}

class Controller extends Actor with ActorLogging {
  import Controller._
  import context.dispatcher

  context.system.scheduler.scheduleOnce(10 seconds, self, ReceiveTimeout)

  var cacheLinks = Set.empty[String]
  var children = Set.empty[ActorRef]

  // state
  def receive = {
    case Check(url, depth) =>
      log.debug("{} checking {}", depth, url)
      if (!cacheLinks(url) && depth > 0)
        children += context.actorOf(Props(new Getter(url, depth - 1)))
      cacheLinks += url

    case Getter.Done =>
      children -= sender
      if (children.isEmpty) context.parent ! Result(cacheLinks)

    case ReceiveTimeout =>
      children foreach (_ ! Getter.Abort)
  }
}
