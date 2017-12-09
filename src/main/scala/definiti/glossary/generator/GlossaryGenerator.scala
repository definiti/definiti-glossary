package definiti.glossary.generator

import com.github.rjeschke.txtmark.{Configuration, Processor}
import definiti.glossary.model.{Glossary, TypeInfo, VerificationInfo}

import scala.xml.{NodeSeq, XML}

class GlossaryGenerator {
  private val noContent: NodeSeq = <em>*No doc found for this type*</em>
  private val markdownConfiguration: Configuration = {
    Configuration
      .builder()
      .forceExtentedProfile()
      .build()
  }

  def generateGlossary(glossary: Glossary): NodeSeq = {
    <div class="row">
      {generateSummary(glossary)}
      {generateContent(glossary)}
    </div>
  }

  private def generateSummary(glossary: Glossary): NodeSeq = {
    <div class="col">
        <h3>Types</h3>
        <ul>
          {glossary.types.map(generateTypeSummary)}
        </ul>

        <hr/>

        <h3>Verifications</h3>
        <ul>
          {glossary.verifications.map(generateVerificationSummary)}
        </ul>
      </div>
  }

  private def generateTypeSummary(typeInfo: TypeInfo): NodeSeq = {
    <li>
      <a href={s"#t-${typeInfo.id}"}>{typeInfo.name}</a>
    </li>
  }

  private def generateVerificationSummary(verificationInfo: VerificationInfo): NodeSeq = {
    <li>
      <a href={s"#v-${verificationInfo.id}"}>{verificationInfo.name}</a>
    </li>
  }

  private def generateContent(glossary: Glossary): NodeSeq = {
    <div class="col-fill">
      {glossary.types.map(generateTypeContent)}
      <hr/>
      {glossary.verifications.map(generateVerificationContent)}
    </div>
  }

  private def generateTypeContent(typeInfo: TypeInfo): NodeSeq = {
    <div id={s"t-${typeInfo.id}"}>
      <h1>{typeInfo.name} <small><code>{typeInfo.id}</code></small></h1>

      <div>{typeInfo.content.map(beautifyContent).getOrElse(noContent)}</div>
    </div>
  }

  private def generateVerificationContent(verificationInfo: VerificationInfo): NodeSeq = {
    <div id={s"v-${verificationInfo.id}"}>
      <h1>{verificationInfo.name} <small><code>{verificationInfo.id}</code></small></h1>

      <blockquote>
        <p>Default message: <code>{verificationInfo.message}</code></p>
      </blockquote>

      <div>{verificationInfo.content.map(beautifyContent).getOrElse(noContent)}</div>
    </div>
  }

  private def beautifyContent(content: String): NodeSeq = {
    val generatedContent = Processor.process(content, markdownConfiguration)
    XML.loadString(s"<div>${generatedContent}</div>")
  }
}
