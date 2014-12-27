package coursera.chapter5.toggle

import akka.actor.{Props, ActorSystem}
import akka.testkit.{TestProbe, TestKit, ImplicitSender}
import scala.concurrent.duration._

import org.scalatest._

class ToggleTest extends FlatSpec with Matchers {

  it should "toggle test1" in  {
    implicit val system = ActorSystem("TestSys")
    val toggle = system.actorOf(Props[Toggle])
    val p = TestProbe()

    p.send(toggle, "How are you?")
    p.expectMsg("happy")

    p.send(toggle, "How are you?")
    p.expectMsg("sad")

    p.send(toggle, "unknown")
    p.expectNoMsg(1.second)

    system.shutdown
  }

  it should "toggle test2" in  {
    new TestKit(ActorSystem("TestSys")) with ImplicitSender {
      val toggle = system.actorOf(Props[Toggle])
      toggle ! "How are you?"
      expectMsg("happy")
      toggle ! "How are you?"
      expectMsg("sad")
      toggle ! "unknown"
      expectNoMsg(1.second)
      system.shutdown()
    }
  }
}
