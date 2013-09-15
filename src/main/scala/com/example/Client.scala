package com.example

import scala.util.{Success, Failure}
import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.event.Logging
import akka.io.IO
import spray.json.{JsonFormat, DefaultJsonProtocol}
import spray.can.Http
import spray.httpx.SprayJsonSupport
import spray.client.pipelining._
import spray.util._
import scala.concurrent.Await

object Client extends App {
  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("simple-spray-client")
  import system.dispatcher // execution context for futures below
  val log = Logging(system, getClass)

  import SprayJsonSupport._
  val pipeline = sendReceive

  for (i <- 1 to 10000) {
	  val responseFuture = pipeline {
	    Post("http://127.0.0.1:8080/api/put", "hit")
	  }
	  println(i, Await.result(responseFuture, 10000.millis))
  }
  
  system.shutdown()
  system.awaitTermination()
  sys.exit(0)
}