package helpers

import play.api.data.Forms.nonEmptyText
import play.api.data.Mapping

object Utils:

  def getStringIf(condition: Boolean, result: String): String =
    if (condition) result else ""

  def getUsernamePasswordField: Mapping[String] = nonEmptyText
    .verifying("must have at least 5 characters", s => s.length >= 5)
    .verifying("must have at most 30 characters", s => s.length <= 30)
