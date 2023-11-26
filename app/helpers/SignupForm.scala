package helpers

import controllers.routes
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}

object SignupForm:
  val form: Form[SignupFormResponse] = Form(
    mapping(
      "username" -> Utils.getUsernamePasswordField,
      "password" -> Utils.getUsernamePasswordField,
      "repeatPassword" -> Utils.getUsernamePasswordField,
    )(SignupFormResponse.apply)(SignupFormResponse.reverseApply)
  )
  val formSubmitUrl = routes.LoginController.signup()

case class SignupFormResponse(username: String, password: String, repeatPassword: String)

object SignupFormResponse:
  def reverseApply(form: SignupFormResponse): Option[(String, String, String)] =
    Some(form.username, form.password, form.repeatPassword)