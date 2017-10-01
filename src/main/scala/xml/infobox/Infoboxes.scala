package xml.infobox

import java.nio.file.Paths

import xml.infobox.Infoboxes._

import scala.io.Source

object Infoboxes {
  val pathKey = "path"

  //skip comments, header, footer
  def skipNoise(s: String): Boolean = s.startsWith("<!--") || s.startsWith("{{") || s.startsWith("}}")

  // take only keys in lower case
  def extractPropertyKey(s: String): Option[String] = {
    s.split("=").headOption.map(_.trim.dropWhile(c => c == ' ' || c == '|').toLowerCase)
  }
}

trait Infoboxes {
  def getKeys(fileName: String): Seq[String] = {
    Infoboxes.pathKey +:
    Source.fromFile(Paths.get("config", fileName).toFile).getLines()
      .filterNot(skipNoise)
      .map(extractPropertyKey)
      .flatten
      .to[Seq]
  }

  val keys: Seq[String]

  def properties: Map[String, String] = keys.map(e => e -> "").toMap
}

object Settlement extends Infoboxes {
  override val keys: Seq[String] = getKeys("settlement-props.txt")
}

object Person extends Infoboxes {
  override val keys: Seq[String] = getKeys("person-props.txt")
}

object Ort extends Infoboxes {
  override val keys: Seq[String] = getKeys("ort-props.txt")
}

object Writer extends Infoboxes {
  override val keys: Seq[String] = getKeys("writer-props.txt")
}