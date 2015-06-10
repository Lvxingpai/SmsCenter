package com.lvxingpai.smscenter

import com.lvxingpai.appconfig.AppConfig

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * Created by zephyre on 6/10/15.
 */
object GlobalConf {
  lazy val conf = Await.result(AppConfig.buildConfig(confKeys = Some(Seq("smscenter" -> "smscenter"))), 10 seconds)
}
