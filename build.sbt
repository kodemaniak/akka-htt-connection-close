organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.10.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://nightlies.spray.io/"
)

libraryDependencies ++= Seq(
  "io.spray"            %  "spray-can"     % "1.2-20130912",
  "io.spray"            %   "spray-routing" % "1.2-20130912",
  "io.spray"            %   "spray-client"  % "1.2-20130912",
  "io.spray"            %   "spray-routing" % "1.2-20130912",
  "io.spray"            %   "spray-testkit" % "1.2-20130912" % "test",
  "io.spray"		%%   "spray-json"    % "1.2.5",
  "com.typesafe.akka"   %%  "akka-actor"    % "2.2.1",
  "com.typesafe.akka"   %%  "akka-testkit"  % "2.2.1" % "test",
  "org.specs2"          %%  "specs2"        % "1.14" % "test"
)

seq(Revolver.settings: _*)
