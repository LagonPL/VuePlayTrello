import com.typesafe.sbt.packager.MappingsHelper._
mappings in Universal ++= directory(baseDirectory.value / "public")

name := "play-vue-webpack"

version := "1.1"

scalaVersion := "2.12.2"

val playPac4jVersion = "5.0.0-SNAPSHOT"
val pac4jVersion = "2.2.0"
val playVersion = "2.6.6"

lazy val `play-vue-webpack` = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

libraryDependencies ++= Seq(guice, filters, jdbc  ,ws , specs2 % Test, ehcache, // or cacheApi
  "org.pac4j" %% "play-pac4j" % playPac4jVersion,
  "org.pac4j" % "pac4j-http" % pac4jVersion,
  "org.pac4j" % "pac4j-cas" % pac4jVersion,
  "org.pac4j" % "pac4j-openid" % pac4jVersion exclude("xml-apis" , "xml-apis"),
  "org.pac4j" % "pac4j-oauth" % pac4jVersion,
  "org.pac4j" % "pac4j-saml" % pac4jVersion,
  "org.pac4j" % "pac4j-oidc" % pac4jVersion exclude("commons-io" , "commons-io"),
  "org.pac4j" % "pac4j-gae" % pac4jVersion,
  "org.pac4j" % "pac4j-jwt" % pac4jVersion exclude("commons-io" , "commons-io"),
  "org.pac4j" % "pac4j-ldap" % pac4jVersion,
  "org.pac4j" % "pac4j-sql" % pac4jVersion,
  "org.pac4j" % "pac4j-mongo" % pac4jVersion,
  "org.pac4j" % "pac4j-kerberos" % pac4jVersion,
  "org.pac4j" % "pac4j-couch" % pac4jVersion,
  "com.typesafe.play" % "play-cache_2.12" % playVersion,
  "commons-io" % "commons-io" % "2.4",
  "be.objectify" %% "deadbolt-java" % "2.6.1")
/*val appDependencies = Seq(
  "com.feth" % "play-authenticate_2.11" % "0.8.3"
)*/

// Play framework hooks for development
PlayKeys.playRunHooks += WebpackServer(file("./front"))

unmanagedResourceDirectories in Test +=  baseDirectory ( _ /"target/web/public/test" ).value

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers ++= Seq(Resolver.mavenLocal, "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/")

/*resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.io/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns)
resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.io/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns)


resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.io/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns)
resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.io/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns)*/

// Production front-end build
lazy val cleanFrontEndBuild = taskKey[Unit]("Remove the old front-end build")

cleanFrontEndBuild := {
  val d = file("public/bundle")
  if (d.exists()) {
    d.listFiles.foreach(f => {
      if(f.isFile) f.delete
    })
  }
}

lazy val frontEndBuild = taskKey[Unit]("Execute the npm build command to build the front-end")

frontEndBuild := {
  println(Process("npm install", file("front")).!!)
  println(Process("npm run build", file("front")).!!)
}

frontEndBuild := (frontEndBuild dependsOn cleanFrontEndBuild).value

dist := (dist dependsOn frontEndBuild).value



