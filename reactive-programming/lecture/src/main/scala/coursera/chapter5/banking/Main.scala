package coursera.chapter5.banking

import akka.actor.Actor
import akka.actor.Props
import akka.event.LoggingReceive

// in sbt
// > run-main akka.Main coursera.chapter5.banking.Main
class Main extends Actor {

  val accountA = context.actorOf(Props[Account], "accountA")
  val accountB = context.actorOf(Props[Account], "accountB")

  accountA ! Account.Deposit(50)

  def receive = LoggingReceive {
    case Account.Done => transfer(50)
  }

  def transfer(amount: BigInt): Unit = {
    // transcation
    val tx = context.actorOf(Props[WireTransfer], "tx")

    tx ! WireTransfer.Transfer(accountA, accountB, amount)
    context.become(LoggingReceive {
      case WireTransfer.Done =>
        println("successfully transfered")
        context.stop(self)
    })
  }
}
