name := """FleetManagement.v1"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaCore,
   evolutions,
  javaWs,
   "org.olap4j" % "olap4j" % "0.9.7.309-JS-3",
  "org.xerial" % "sqlite-jdbc" % "3.8.11.1",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "com.typesafe.play" %% "play-mailer" % "2.4.0",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "com.itextpdf" % "itextpdf" % "5.5.6",
 "com.lowagie" % "itext" % "4.2.2",
 "net.sf.jasperreports" % "jasperreports" % "6.1.1",
  "net.sourceforge.dynamicreports" % "dynamicreports-core" % "4.0.1",
  "net.sourceforge.dynamicreports" % "dynamicreports-googlecharts" % "4.0.1",
  "net.sourceforge.dynamicreports" % "dynamicreports-parent" % "4.0.1",
  "net.sourceforge.dynamicreports" % "dynamicreports-adhoc" % "4.0.1",
  "org.jsoup" % "jsoup" % "1.8.1"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
//resolvers := Seq("Jasper" at "http://jasperreports.sourceforge.net/maven2") 
resolvers := Seq("Olap4j" at "http://dev.mapfish.org/maven/repository/org/olap4j/olap4j/0.9.7.309-JS-3/")
