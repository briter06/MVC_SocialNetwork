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
   * Private logger instances
   */
  private val logger = Logger(this.getClass)

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
            logger.info(s"Successful login for user ${u.username}")
            Redirect(routes.HomeController.index())
              .withSession(Global.SESSION_USERNAME_KEY -> u.username)
          case _ =>
            logger.error(s"Invalid username/password")
            Redirect(routes.IndexController.index())
            .flashing("LoginError" -> "Invalid username/password")
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
            logger.error(s"Username ${u} is already taken")
            Redirect(s"${routes.IndexController.index().toString}?showLogin=false")
              .flashing("SignupError" -> "Username is already taken.")
          case _ if form.password != form.repeatPassword =>
            logger.error(s"The passwords are not equal for user ${form.username}")
            Redirect(s"${routes.IndexController.index().toString}?showLogin=false")
              .flashing("SignupError" -> "The passwords are not equal.")
          case _ =>
            userDao.createUser(form.username, form.password)
            logger.info(s"User ${form.username} successfully created")
            Redirect(routes.IndexController.index())
              .flashing("LoginInfo" -> "Please login.")
    )
  }

  /**
   * POST handler to reset the session and logout the user
   * @return - New action with a new session
   */
  def logout(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    logger.info(s"Successfully logout")
    Redirect(routes.IndexController.index()).withNewSession
  }
}
