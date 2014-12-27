package coursera.chapter5.banking

import akka.actor.Actor
import akka.actor.Props
import akka.event.LoggingReceive

object Account {
  case class Deposit(amount: BigInt) {
    require(amount > 0)
  }
  
  case class Withdraw(amount: BigInt) {
    require(amount > 0)
  }
  
  case object Done
  case object Failed
}

class Account extends Actor {

  import Account._

  var balance = BigInt(0)
  
  def receive = LoggingReceive {
    case Deposit(amount) => 
      balance += amount
      sender ! Done                      
    case Withdraw(amount) if amount <= balance =>
      balance -= amount
      sender ! Done
    case _ =>
      sender ! Failed
  }
}


