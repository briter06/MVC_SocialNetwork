package helpers

import play.api.data.Forms.nonEmptyText
import play.api.data.Mapping

/**
 * Object with frequently used features
 */
object Utils:

  /**
   * Basic implementation of a ternary with only true option
   * @param condition - Boolean condition to evaluate
   * @param result - Return string in case condition is true
   * @return - Result of the ternary
   */
  def getStringIf(condition: Boolean, result: String): String =
    if (condition) result else ""

  /**
   * Configuration for the username and password fields in the form
   * @return - Configuration of the field
   */
  def getUsernamePasswordField: Mapping[String] = nonEmptyText
    .verifying("must have at least 5 characters", s => s.length >= 5)
    .verifying("must have at most 30 characters", s => s.length <= 30)
