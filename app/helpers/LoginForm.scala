package helpers

import controllers.routes
import play.api.data.Form
import play.api.data.Forms.mapping

/**
 * Object to wrap the elements for the login form
 */
object LoginForm:
  val form: Form[LoginFormResponse] = Form(
    mapping(
      "username" -> Utils.getUsernamePasswordField,
      "password" -> Utils.getUsernamePasswordField,
    )(LoginFormResponse.apply)(LoginFormResponse.reverseApply)
  )

/**
 * Case class representing the structure of the response of the login form
 * @param username - Username for the login
 * @param password - Possible password of the user
 */
case class LoginFormResponse(username: String, password: String)

/**
 * Companion object to implement the reverse apply
 */
object LoginFormResponse:
  def reverseApply(form: LoginFormResponse): Option[(String, String)] =
    Some(form.username, form.password)