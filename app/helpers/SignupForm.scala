package helpers

import play.api.data.Form
import play.api.data.Forms.mapping

/**
 * Object to wrap the elements for the signup form
 */
object SignupForm:
  val form: Form[SignupFormResponse] = Form(
    mapping(
      "username" -> Utils.getUsernamePasswordField,
      "password" -> Utils.getUsernamePasswordField,
      "repeatPassword" -> Utils.getUsernamePasswordField,
    )(SignupFormResponse.apply)(SignupFormResponse.reverseApply)
  )

/**
 * Case class representing the structure of the response of the signup form
 * @param username - Username of the new user
 * @param password - Password of the new user
 * @param repeatPassword - Confirmation of the password
 */
case class SignupFormResponse(username: String, password: String, repeatPassword: String)

/**
 * Companion object to implement the reverse apply
 */
object SignupFormResponse:
  def reverseApply(form: SignupFormResponse): Option[(String, String, String)] =
    Some(form.username, form.password, form.repeatPassword)