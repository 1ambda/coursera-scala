package coursera.chapter5.crawler

import akka.actor.{Actor, ActorRef, Props}

object Receptionist {
  private case class Job(client: ActorRef, url: String)

  case class Get(url: String)
  case class Result(url: String, links: Set[String])
  case class Failed(url: String)
}

class Receptionist extends Actor {
  import Receptionist._

  // initial actor state
  def receive = waiting

  val waiting: Receive = {
    case Get(url) =>
      context.become(runNext(Vector(Job(sender, url))))
  }

  // running actor state
  def running(q: Vector[Job]): Receive = {
    case Get(url) =>
      context.become(enqueueJob(q, Job(sender, url)))

    case Controller.Result(links) =>
      val Job(client, url) = q.head
      client ! Result(url, links)
      context.stop(sender) // stop the controller
      context.become(runNext(q.tail))
  }

  var reqNo = 0

  // helper methods
  def runNext(q: Vector[Job]): Receive = {
    reqNo += 1

    if (q.isEmpty) waiting
    else {
      val controller = context.actorOf(Props[Controller], s"crawlerController$reqNo")
      controller ! Controller.Check(q.head.url, 2)
      running(q)
    }
  }

  def enqueueJob(q: Vector[Job], j: Job): Receive = {
    if (q.size > 3) {
      sender ! Failed(j.url)
      running(q)
    } else running(q :+ j)
  }
}
