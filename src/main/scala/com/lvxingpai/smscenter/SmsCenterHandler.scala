package com.lvxingpai.smscenter

import java.io.{ BufferedReader, InputStreamReader }
import java.net.{ URL, URLEncoder }
import scala.collection.JavaConversions._
import com.twitter.util.{ Future, FuturePool }
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

import scala.language.postfixOps

/**
 * Created by zephyre on 6/9/15.
 */
class SmsCenterHandler extends SmsCenter.FutureIface {
  override def sendSms(message: String, recipients: Seq[String]): Future[String] =
    SmsCenterHandler.sendSms(message, recipients: _*)
}

object SmsCenterHandler {

  def sendSms(message: String, recipients: String*): Future[String] = {
    val conf = GlobalConf.conf.getConfig("smscenter")

    val host = conf.getString("provider.host")
    val port = conf.getInt("provider.port")
    val user = conf.getString("provider.user")
    val password = conf.getString("provider.password")
    val destination = recipients mkString ","
    val contents = URLEncoder.encode(message, "GBK")
    val requestUrl = s"http://$host:$port/QxtSms/QxtFirewall?OperID=$user&OperPass=$password&DesMobile=$destination" +
      s"&Content=$contents&ContentType=15"

    FuturePool.unboundedPool {
      val jUrl = new URL(requestUrl)
      val yc = jUrl.openConnection()
      yc.setRequestProperty("Connection", "keep-alive")
      yc.setRequestProperty("Proxy-Connection", "keep-alive")
      yc.setRequestProperty("Accept", "*/*")
      val in = new BufferedReader(new InputStreamReader(yc.getInputStream))
      val response = in.readLine()
      in.close()

      val doc = Jsoup.parse(response, "", Parser.xmlParser())
      val nodes = doc.select("response>code").toSeq
      if (nodes isEmpty)
        throw SmsCenterException(-1, "No response code detected")
      else {
        val code = try {
          nodes.head.text.toInt
        } catch {
          case e: NumberFormatException => throw SmsCenterException(-1, "No response code detected")
        }
        if (code != 3 && code != 1)
          throw SmsCenterException(code, s"Invalid response code: $code")
      }
      val msgIdNodes = doc.select("response>message>msgid").toSeq
      if (msgIdNodes isEmpty)
        throw SmsCenterException(-1, "No message ID detected")
      else
        msgIdNodes.head.text
    }
  }
}
