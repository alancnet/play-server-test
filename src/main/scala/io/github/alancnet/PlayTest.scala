package io.github.alancnet {

import java.io.File

import play.api.ApplicationLoader.Context
import play.api.http.HttpRequestHandler
import play.api.mvc._
import play.api.routing._
import play.api.routing.sird._
import play.api.{BuiltInComponentsFromContext, Application, Environment, ApplicationLoader}
import play.mvc.Results


object PlayTest {

  class Dummy {}

  def main(args: Array[String]): Unit = {
    def startWebServer = {
      val environment = new Environment(
        new File("."),
        classOf[Dummy].getClassLoader,
        play.api.Mode.Dev
      )
      val context = play.api.ApplicationLoader.createContext(environment)
      //val application = ApplicationLoader(context).load(context)

      val application = new ApplicationLoader {
        override def load(context: Context): Application =
          new BuiltInComponentsFromContext(context) {
            def routes:Router.Routes = {
              case GET(p"/") => controllers.Application.index
            }
            override def router: Router = Router.from(routes)
          }.application
      }.load(context)

      play.api.Play.start(application)

      play.core.server.NettyServer.fromApplication(
        application
      )

    }

    startWebServer

  }
}

}
package controllers {
  import play.api._
  import play.api.mvc._
  object Application extends Controller {
    def index = Action {
      Ok("Hello World")
    }
  }
}