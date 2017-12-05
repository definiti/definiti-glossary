package definiti.glossary.generator

import com.github.rjeschke.txtmark.{Configuration, Processor}
import definiti.glossary.model.TypeInfo

import scala.xml.{NodeSeq, XML}

class GlossaryGenerator {
  private val noContent: NodeSeq = <em>*No doc found for this type*</em>
  private val markdownConfiguration: Configuration = {
    Configuration
      .builder()
      .forceExtentedProfile()
      .build()
  }

  def generateGlossary(types: Seq[TypeInfo]): NodeSeq = {
    <div class="row">
      <div class="col">
        <ul>
          {types.map(generateTypeSummary)}
        </ul>
      </div>
      <div class="col-fill">
        {types.map(generateTypeContent)}
      </div>
    </div>
  }

  private def generateTypeSummary(typeInfo: TypeInfo): NodeSeq = {
    <li>
      <a href={s"#${typeInfo.id}"}>{typeInfo.name}</a>
    </li>
  }

  private def generateTypeContent(typeInfo: TypeInfo): NodeSeq = {
    <div id={typeInfo.id}>
      <h1>{typeInfo.name} <small><code>{typeInfo.id}</code></small></h1>

      <div>{typeInfo.content.map(beautifyContent).getOrElse(noContent)}</div>
    </div>
  }

  private def beautifyContent(content: String): NodeSeq = {
    val generatedContent = Processor.process(content, markdownConfiguration)
    XML.loadString(s"<div>${generatedContent}</div>")
  }
}
