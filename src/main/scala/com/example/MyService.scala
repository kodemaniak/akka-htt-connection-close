package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.headers.Connection
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
object MyService extends App {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val myRoute =
    path("api" / "put") {
      post {
        complete {
          Counter.counter += 1
          if (Counter.counter % 20 != 0) HttpResponse(headers = List(Connection("keep-alive")))
          else HttpResponse(headers = List(Connection("close")))
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(myRoute, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}

object Counter {
  var counter = 0
}