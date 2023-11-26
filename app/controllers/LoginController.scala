package controllers

import helpers.{LoginForm, LoginFormResponse, SignupForm, SignupFormResponse}
import models.{Global, User, UserDao}
import play.api.*
import play.api.data.Form
import play.api.mvc.*
import javax.inject.*

/**
 * Controller to handle the login, signup and logout features
 * @param controllerComponents - Injected controller components
 * @param userDao - Injected DAO of the User model
 */
@Singleton
class LoginController @Inject()(val controllerComponents: ControllerComponents, val userDao: UserDao) extends BaseController {

  /**
   * POST handler to login a user based on the username and password
   * @return - New action depending on the result of the login attempt
   */
  def login(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    LoginForm.form.bindFromRequest().fold(
      (formWithErrors: Form[LoginFormResponse]) =>
        val values: Seq[(String, String)] = formWithErrors.errors.map(
          error => ("LoginError", s"The field '${error.key}' ${error.message}")
        )
        Redirect(routes.IndexController.index()).flashing(values:_*),
      (form: LoginFormResponse) =>
        val possibleUser = userDao.getUser(form.username)
        possibleUser match
          case Some(u) if u.password == form.password =>
            Redirect(routes.HomeController.index())
              .withSession(Global.SESSION_USERNAME_KEY -> u.username)
          case _ => Redirect(routes.IndexController.index())
            .flashing("LoginError" -> "Invalid username/password.")
    )
  }

  /**
   * POST handler to signup a user based on the username and password
   * @return - New action depending on the result of the signup attempt
   */
  def signup(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    SignupForm.form.bindFromRequest().fold(
      (formWithErrors: Form[SignupFormResponse]) =>
        val values: Seq[(String, String)] = formWithErrors.errors.map(
          error => ("SignupError", s"The field '${
            error.key match
              case "repeatPassword" => "Repeat Password"
              case value => value
          }' ${error.message}")
        )
        Redirect(s"${routes.IndexController.index().toString}?showLogin=false").flashing(values: _*),
      (form: SignupFormResponse) =>
        val possibleUser = userDao.getUser(form.username)
        possibleUser match
          case Some(u) =>
            Redirect(s"${routes.IndexController.index().toString}?showLogin=false")
              .flashing("SignupError" -> "Username is already taken.")
          case _ if form.password != form.repeatPassword =>
            Redirect(s"${routes.IndexController.index().toString}?showLogin=false")
              .flashing("SignupError" -> "The passwords are not equal.")
          case _ =>
            userDao.createUser(form.username, form.password)
            Redirect(routes.IndexController.index())
              .flashing("LoginInfo" -> "Please login.")
    )
  }

  /**
   * POST handler to reset the session and logout the user
   * @return - New action with a new session
   */
  def logout(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.IndexController.index()).withNewSession
  }
}
