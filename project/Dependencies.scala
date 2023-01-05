import sbt._

object Dependencies {
  object Testing {
    private val _version = "3.2.14"
    final val * = Seq(
      "org.scalactic" %% "scalactic" % _version,
      "org.scalatest" %% "scalatest" % _version % Test
    )
  }
  object Lorem {
    private val _version = "2.1"
    final val * = Seq("com.thedeanda" % "lorem" % _version)
  }
  object LogUtils {
    final val nameof =
      "com.github.dwickern" %% "scala-nameof" % "4.0.0" % "provided"
    final val sourceCode = "com.lihaoyi" %% "sourcecode" % "0.3.0"
    final val * = Seq(nameof, sourceCode)
  }
  object GeniusFish {
    final val config = "fish.genius" % "config_2.13" % "1.0.6"
    final val * = Seq(config)
  }
}
