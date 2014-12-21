package coursera.chapter2

import org.scalatest._

class DigitalCircuitTest extends FlatSpec with Matchers {

  import DigitalCircuit._

  // test circuit

  it should "" in  {
    object test extends Circuits with Parameters
    import test._
    val in1, in2, sum, carry = new Wire

    halfAdder(in1, in2, sum, carry)
    probe("sum", sum)
    probe("carry", carry)

    in1.setSignal(true)
    test.run()
    in2.setSignal(true)
    test.run()
  }
}
