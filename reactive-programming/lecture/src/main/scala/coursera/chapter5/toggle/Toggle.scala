package coursera.chapter5.toggle

import akka.actor.Actor

class Toggle extends Actor {
  // happy state
  def happy: Receive = {
    case "How are you?" =>
      sender ! "happy"
      context become sad
  }

  // sad state
  def sad: Receive = {
    case "How are you?" =>
      sender ! "sad"
      context become happy
  }

  // initial state: happy
  def receive = happy
}
