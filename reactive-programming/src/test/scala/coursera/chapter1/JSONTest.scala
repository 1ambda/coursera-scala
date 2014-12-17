package coursera.chapter1

import org.scalatest._

class JSONTest extends FlatSpec with Matchers {

  import JSON._

  val data = JObj(Map(
    "firstName"   -> JStr("Jason"),
    "lastName"    -> JStr("Bone"),
    "phoneNumber" -> JSeq(List(
      JObj(Map(
        "type" -> JStr("home"), "number" -> JStr("212 555 3347"),
        "type" -> JStr("fax"),  "number" -> JStr("33312 555 3347")
      ))
    ))
  ))

  "show" should "make JSON as String" in  {
    println(show(data))
  }
}
