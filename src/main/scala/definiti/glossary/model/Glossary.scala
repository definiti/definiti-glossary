package definiti.glossary.model

case class Glossary(
  types: Seq[TypeInfo],
  verifications: Seq[VerificationInfo]
)

case class TypeInfo(id: String, name: String, content: Option[String])

case class VerificationInfo(id: String, name: String, message: String, content: Option[String])