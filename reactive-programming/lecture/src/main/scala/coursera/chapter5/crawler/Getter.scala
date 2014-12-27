package coursera.chapter5.crawler

import akka.actor.{Actor, Status}
import akka.pattern.pipe
import java.util.concurrent.Executor
import scala.concurrent._

object Getter {
  case object Done
  case object Abort

  // regex
  val A_TAG = "(?i)<a ([^>]+)>.+?</a>".r
  val HREF_ATTR = """\s*(?i)href\s*=\s*(?:"([^"]*)"|'([^']*)'|([^'">\s]+))\s*""".r

  def findLinks(body: String): Iterator[String] = {
    for {
      anchor <- A_TAG.findAllMatchIn(body)
      HREF_ATTR(dquot, quot, bare) <- anchor.subgroups
    } yield if (dquot != null) dquot
    else if (quot != null) quot
    else bare 
  }
}

class Getter(url: String, depth: Int) extends Actor {
  import Getter._

  implicit val exec = context.dispatcher.asInstanceOf[Executor with ExecutionContext]

  val f = WebClient get url pipeTo self

  // state
  def receive = {
    case body: String =>
      for (link <- findLinks(body)) context.parent ! Controller.Check(link, depth)
      stop()
    case _ =>
      stop ()
  }

  def stop(): Unit = {
    context.parent ! Done
    context.stop(self)
  }
}
