package fish.genius.logging

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec

class LoggableSpec extends AnyFlatSpec with GivenWhenThen {
  it should "write log messages" in new Loggable {
    warning("This is a warning")
    error("This is an error")
    info("This is information")
    debug("This is debug information")
    exception(new IllegalArgumentException("Illegal Argument"))
  }
}
