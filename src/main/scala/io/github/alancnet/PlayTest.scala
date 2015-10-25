package io.github.alancnet

import java.io.File

import play.api.{Environment, ApplicationLoader}

object PlayTest {
  class Dummy{}
  def main(args:Array[String]):Unit = {
    def startWebServer = {
      val environment = new Environment(
        new File("."),
        classOf[Dummy].getClassLoader,
        play.api.Mode.Dev
      )
      val context = play.api.ApplicationLoader.createContext(environment)
      val application = ApplicationLoader(context).load(context)

      play.api.Play.start(application)

      play.core.server.NettyServer.fromApplication(
        application
      )
    }

    startWebServer

  }
}
