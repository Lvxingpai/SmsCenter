package com.lvxingpai.smscenter

import com.typesafe.config.ConfigFactory

import scala.language.postfixOps

/**
 * Created by zephyre on 6/10/15.
 */
object GlobalConf {
  lazy val conf = ConfigFactory.load()
}
