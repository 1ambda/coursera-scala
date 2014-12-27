package coursera.chapter5.banking

import akka.actor.{Actor, Props, ActorRef}

object WireTransfer {
  case class Transfer(from: ActorRef, to: ActorRef, amount: BigInt)
  case object Done
  case object Failed
}

class WireTransfer extends Actor {
  import WireTransfer._

  def receive: Receive = {
    case Transfer(from, to, amount) =>
      from ! Account.Withdraw(amount)
      context.become(awaitFrom(to, amount, sender))
    case _ => sender ! Failed
  }

  def awaitFrom(to: ActorRef, amount: BigInt, customer: ActorRef): Receive = {
    case Account.Done =>
      to ! Account.Deposit(amount)
      context.become(awaitTo(customer))
    case Account.Failed => 
      customer ! Failed
      context.stop(self)
  }

  def awaitTo(customer: ActorRef): Receive = {
    case Account.Done =>
      customer ! Done
      context.stop(self)
    case Account.Failed =>
      customer ! Failed
      context.stop(self)
  }
}
