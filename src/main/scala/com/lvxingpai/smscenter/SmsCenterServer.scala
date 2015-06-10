package com.lvxingpai.smscenter

import java.net.InetSocketAddress

import com.lvxingpai.smscenter.SmsCenter.FinagledService
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import org.apache.thrift.protocol.TBinaryProtocol.Factory

/**
 * Created by zephyre on 6/9/15.
 */
object SmsCenterServer extends App {
  start()

  def start(): Unit = {
    val conf = GlobalConf.conf.getConfig("smscenter")

    val serviceName = conf.getString("serviceName")
    val maxConcur = conf.getInt("maxConcurrentRequests")
    val bindHost = conf.getString("bind.host")
    val bindPort = conf.getInt("bind.port")

    val service = new FinagledService(new SmsCenterHandler, new Factory())

    ServerBuilder()
      .bindTo(new InetSocketAddress(bindHost, bindPort))
      .codec(ThriftServerFramedCodec())
      .name(serviceName)
      .maxConcurrentRequests(maxConcur)
      .build(service)
  }
}
