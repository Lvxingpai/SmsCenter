package com.lvxingpai.smscenter

import com.lvxingpai.appconfig.EtcdConfBuilder
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * Created by zephyre on 6/10/15.
 */
object GlobalConf {
  lazy val conf = {
    val defaultConf = ConfigFactory.load()
    Await.result(EtcdConfBuilder().addKey("smscenter").build(), 10 seconds) withFallback defaultConf
  }
}
