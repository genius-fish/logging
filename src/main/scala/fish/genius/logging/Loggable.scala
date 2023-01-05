package fish.genius.logging

import fish.genius.config.Configurable

trait Loggable {
  def info(message: String, traceId: Option[TraceId] = None)(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] = {
    Logger.log(
      message = message,
      level = Info,
      traceId = traceId
    )(line, file, name)
  }

  def -->(message: String, traceId: Option[TraceId] = None)(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] =
    info(message, traceId)

  def log(message: String, traceId: Option[TraceId] = None)(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] =
    info(message, traceId)

  def warning(
      message: String,
      traceId: Option[TraceId] = None
  )(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] =
    Logger.log(
      message = message,
      level = Warning,
      traceId = traceId
    )(line, file, name)

  def !-->(message: String, traceId: Option[TraceId] = None)(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] =
    warning(message, traceId)

  def debug(message: String, traceId: Option[TraceId] = None)(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] =
    Logger.log(
      message = message,
      level = Debug,
      traceId = traceId
    )(line, file, name)

  def --->(message: String, traceId: Option[TraceId] = None)(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] =
    debug(message, traceId)

  def error(message: String, traceId: Option[TraceId] = None)(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] =
    Logger.log(
      message = message,
      level = Error,
      traceId = traceId
    )(line, file, name)

  def !!-->(message: String, traceId: Option[TraceId] = None)(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] =
    error(message, traceId)

  def exception(
      cause: Throwable,
      traceId: Option[TraceId] = None
  )(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] =
    Logger.log(
      message = cause.getMessage,
      level = Error,
      traceId = traceId
    )(line, file, name)

  def !!!-->(
      cause: Throwable,
      traceId: Option[TraceId] = None
  )(implicit
      line: sourcecode.Line,
      file: sourcecode.File,
      name: sourcecode.FullName
  ): Option[String] = exception(cause, traceId)

}
