package definiti.glossary.generator

import definiti.core.ast._
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
    library.types.flatMap {
      case aliasType: AliasType =>
        Some(TypeInfo(
          id = aliasType.fullName,
          name = StringUtils.lastPart(aliasType.name),
          content = aliasType.comment.map(normalizeComment)
        ))
      case definedType: DefinedType =>
        Some(TypeInfo(
          id = definedType.fullName,
          name = StringUtils.lastPart(definedType.name),
          content = definedType.comment.map(normalizeComment)
        ))
      case enum: Enum =>
        Some(TypeInfo(
          id = enum.fullName,
          name = StringUtils.lastPart(enum.name),
          content = enum.comment.map(normalizeComment)
        ))
      case _ => None
    }.sortWith(_.name < _.name)
  }

  private def extractVerifications(library: Library): Seq[VerificationInfo] = {
    library.verifications.map { verification =>
      VerificationInfo(
        id = verification.fullName,
        name = verification.name,
        message = verification.message match {
          case literal: LiteralMessage => literal.message
          case typed: TypedMessage => s"${typed.message}(${typed.types.map(_.readableString).mkString(", ")})"
        },
        content = verification.comment.map(normalizeComment)
      )
    }.sortWith(_.name < _.name)
  }

  private def normalizeComment(comment: String): String = {
    comment
      .split("\n")
      .map(line => line.replaceAll("^\\s+\\*?\\s+", ""))
      .mkString("\n")
  }
}
