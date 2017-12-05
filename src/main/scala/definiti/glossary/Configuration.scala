package definiti.glossary

import java.nio.file.{Path, Paths}

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.Logger
import definiti.glossary.utils.StringUtils

private[glossary] class Configuration(config: Config) {
  private val logger = Logger(getClass)

  def this() {
    this(ConfigFactory.load())
  }

  lazy val destination: Path = getFirstDefinedPath(
    "definiti.glossary.destination",
    "definiti.build.destination"
  ).getOrElse(Paths.get("target", "glossary"))

  private def getFirstDefinedPath(keys: String*): Option[Path] = {
    keys
      .map(getPathOpt)
      .collectFirst {
        case Some(path) => path
      }
  }

  private def getPathOpt(configurationPath: String): Option[Path] = {
    if (config.hasPath(configurationPath)) {
      val rawPath = config.getString(configurationPath)
      val path = Paths.get(rawPath)
      Some(path)
    } else {
      None
    }
  }
}