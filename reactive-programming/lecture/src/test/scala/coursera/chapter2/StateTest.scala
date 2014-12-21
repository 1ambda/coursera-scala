package coursera.chapter2

import org.scalatest._

class StateTest extends FlatSpec with Matchers {

  import States._

  "REPEAT UNTIL" should "make x 4" in  {
    var x = 0
    
    REPEAT {
      x = x + 1
    } UNTIL (x > 3)

    println(x)
  }

}
