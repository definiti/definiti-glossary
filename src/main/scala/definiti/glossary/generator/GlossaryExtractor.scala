package definiti.glossary.generator

import definiti.core.ast.{AliasType, DefinedType, Library}
import definiti.glossary.model.{Glossary, TypeInfo, VerificationInfo}
import definiti.glossary.utils.StringUtils

class GlossaryExtractor {
  def extractGlossary(library: Library): Glossary = {
    Glossary(
      types = extractTypes(library),
      verifications = extractVerifications(library)
    )
  }

  private def extractTypes(library: Library): Seq[TypeInfo] = {
    library.types.flatMap { case (fullName, typ) => typ match {
      case aliasType: AliasType =>
        Some(TypeInfo(
          id = fullName,
          name = StringUtils.lastPart(aliasType.name, '.'),
          content = aliasType.comment.map(normalizeComment)
        ))
      case definedType: DefinedType =>
        Some(TypeInfo(
          id = fullName,
          name = StringUtils.lastPart(definedType.name, '.'),
          content = definedType.comment.map(normalizeComment)
        ))
      case _ => None
    }}.toSeq
  }

  private def extractVerifications(library: Library): Seq[VerificationInfo] = {
    library.verifications.map { case (fullName, verification) =>
      VerificationInfo(
        id = fullName,
        name = verification.name,
        message = verification.message,
        content = verification.comment.map(normalizeComment)
      )
    }.toSeq
  }

  private def normalizeComment(comment: String): String = {
    comment
      .split("\n")
      .map(line => line.replaceAll("^\\s+\\*?\\s+", ""))
      .mkString("\n")
  }
}
