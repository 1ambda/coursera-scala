package coursera.chapter2

object States {

  class BankAccount {
    private var balance = 0

    def deposit(amount: Int): Unit = {
      if (amount > 0) balance = balance + amount
    }

    def withdraw(amount: Int): Unit = {
      if (0 < amount && amount <= balance) balance = balance - amount
      else throw new Error("insufficient")
    }
  }

  class BankAccountProxy(ba: BankAccount) {
    def deposit(amount: Int) = ba.deposit(amount)
    def withdraw(amount: Int) = ba.withdraw(amount)
  }

  def WHILE(condition: => Boolean)(body: => Unit): Unit = {
    if (condition) {
      body
      WHILE(condition)(body)
    } else ()
  }

  // from: https://gist.github.com/metasim/7503601
  def REPEAT(body: => Unit) = new {
    def UNTIL(condition: => Boolean): Unit = {
      body
      if (condition) ()
      else UNTIL(condition)
    }
  }
}
