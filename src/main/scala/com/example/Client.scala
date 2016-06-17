package com.example

import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer

import scala.concurrent.{Await, Future}

object Client extends App {
  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("simple-spray-client")
  implicit val materializer = ActorMaterializer()
  import system.dispatcher // execution context for futures below

  for (i <- 1 to 10000) {
    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(RequestBuilding.Post("http://127.0.0.1:8080/api/put", "hit"))
	  println(i, Await.result(responseFuture, 10000.millis))
  }
  
  system.shutdown()
  system.awaitTermination()
  sys.exit(0)
}