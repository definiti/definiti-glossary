package definiti.glossary.plugin

import java.nio.file.Path

import definiti.core._
import definiti.core.ast.{Library, Root}
import definiti.glossary.Configuration
import definiti.glossary.generator.{GlossaryExtractor, GlossaryGenerator}
import definiti.glossary.utils.Resource

class GlossaryGeneratorPlugin extends GeneratorPlugin {
  val config = new Configuration()
  val extractor = new GlossaryExtractor
  val generator = new GlossaryGenerator

  override def name: String = "glossary-generator"

  override def generate(root: Root, library: Library): Map[Path, String] = {
    val glossary = extractor.extractGlossary(library)
    val html = generator.generateGlossary(glossary)
    val template = Resource("template.html").content
    val content = template.replace("{{{content}}}", html.toString())
    Map(
      config.destination.resolve("index.html") -> content
    )
  }
}