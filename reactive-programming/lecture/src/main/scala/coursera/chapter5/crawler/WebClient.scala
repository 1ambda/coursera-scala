package coursera.chapter5.crawler

import akka.actor.{Actor, Props, ReceiveTimeout}
import scala.concurrent.{Promise, Future}
import com.ning.http.client.AsyncHttpClient
import java.util.concurrent.Executor

object WebClient {
  private val async = new AsyncHttpClient
  private val bodySize = 131072 // 120KB

  case class BadStatus(status: Int) extends RuntimeException

  def get(url: String)(implicit exec: Executor): Future[String] = {
    val f = async.prepareGet(url).execute // return Future
    val p = Promise[String]()

    f.addListener(new Runnable {
      def run = {
        val res = f.get // response
        val status = res.getStatusCode

        if (status < 400) p.success(res.getResponseBodyExcerpt(bodySize))
        else p.failure(BadStatus(status))
      }
    }, exec)

    p.future
  }

  // close async http client
  def shutdown(): Unit = async.close()
}
