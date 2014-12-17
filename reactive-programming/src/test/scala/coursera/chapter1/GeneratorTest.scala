package coursera.chapter1

import org.scalatest._

class GeneratorTest extends FlatSpec with Matchers {

  import Generators._

  "booleans" should "return a boolean value" in  {
    println(booleans.generate)
  }

  "pairs" should "return a pair" in  {
    println(pairs.generate)
  }
}
