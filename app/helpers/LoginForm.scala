package helpers

import controllers.routes
import play.api.data.Form
import play.api.data.Forms.mapping

object LoginForm:
  val form: Form[LoginFormResponse] = Form(
    mapping(
      "username" -> Utils.getUsernamePasswordField,
      "password" -> Utils.getUsernamePasswordField,
    )(LoginFormResponse.apply)(LoginFormResponse.reverseApply)
  )
  val formSubmitUrl = routes.LoginController.login()


case class LoginFormResponse(username: String, password: String)

object LoginFormResponse:
  def reverseApply(form: LoginFormResponse): Option[(String, String)] =
    Some(form.username, form.password)