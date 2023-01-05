package fish.genius.logging

import fish.genius.config.Configurable

import java.util.UUID
import scala.util.Try

object Logger extends Configurable {
  private val configuredLogLevel: LogLevel = LogLevel(Try {
    config.getString("genius.logging.level")
  }.toOption)

  private def ifLevelIsEnabled(
      level: LogLevel,
      op: Unit => Option[String]
  ): Option[String] =
    if (level.level >= configuredLogLevel.level) op.apply() else None

  private val useColors: Boolean = Try {
    config.getBoolean("genius.logging.colors")
  }.getOrElse(false)

  private def colorPrefix(level: LogLevel): String =
    if (useColors) level.color.getOrElse("") else ""
  private def colorSuffix(level: LogLevel): String =
    if (useColors) level.color.map(_ => Console.RESET).getOrElse("") else ""

  def log(
      message: String,
      level: LogLevel,
      traceId: Option[TraceId]
  )(
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] = ifLevelIsEnabled(
    level,
    _ => {
      val _log =
        s"${colorPrefix(level)}${level.prefix} ${sourceCodePosition(line, file, name)} - $message ${traceId
            .map(name => s"($name)")
            .getOrElse("")}${colorSuffix(level)}"
      println(_log)
      Some(_log)
    }
  )

  def sourceCodePosition(
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): String = {
    Option(file)
      .map(_.value)
      .flatMap(_.split('/').lastOption)
      .map(filename => s"${name.value} ($filename):${line.value}")
      .getOrElse("")
  }

}

sealed trait LogLevel {
  def color: Option[String] = None

  def prefix: String

  def level: Int
}

object LogLevel {
  def apply(value: Option[String]): LogLevel = value
    .filter(v => !v.isBlank)
    .map(_.trim.toLowerCase)
    .map {
      case "debug"            => Debug
      case "info"             => Info
      case "warn" | "warning" => Warning
      case "error"            => Error
      case _                  => Info
    }
    .getOrElse(Info)
}

case object Debug extends LogLevel {
  override val color: Option[String] = Some(Console.BLUE)
  override val prefix: String = "[DEBUG] -"
  override val level: Int = 0
}

case object Info extends LogLevel {
  override val color: Option[String] = Some(Console.GREEN)
  override val prefix: String = "[INFO] -"
  override val level: Int = 1
}

case object Warning extends LogLevel {
  override val color: Option[String] = Some(Console.YELLOW)
  override val prefix: String = "[WARN] -"
  override val level: Int = 2
}

case object Error extends LogLevel {
  override val color: Option[String] = Some(Console.RED)
  override def prefix: String = "[ERROR] -"
  override val level: Int = 3
}

sealed trait TraceId {
  def name: String
}

case class SimpleTraceId(name: String) extends TraceId
