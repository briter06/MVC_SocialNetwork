package controllers

import models.{Global, User, UserDao, UserSignup}
import play.api.*
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.mvc.*

import javax.inject.*


@Singleton
class LoginController @Inject()(val controllerComponents: ControllerComponents, val userDao: UserDao) extends BaseController {

  def login() = Action { implicit request: Request[AnyContent] =>
    val formValidationResult: Form[User] = LoginForm.form.bindFromRequest()
    val errorFunction = { (formWithErrors: Form[User]) =>
      BadRequest(views.html.index(true))
    }
    val successFunction = { (user: User) =>
      val possibleUser = userDao.getUser(user.username)
      possibleUser match
        case Some(u) if u.password == user.password =>
          Redirect(routes.HomeController.index())
            .withSession(Global.SESSION_USERNAME_KEY -> user.username)
        case _ => Redirect(routes.IndexController.index())
          .flashing("LoginError" -> "Invalid username/password.")
    }
    formValidationResult.fold(
      errorFunction,
      successFunction
    )
  }

  def signup() = Action { implicit request: Request[AnyContent] =>
    val formValidationResult: Form[UserSignup] = SignupForm.form.bindFromRequest()
    val errorFunction = { (formWithErrors: Form[UserSignup]) =>
      BadRequest(views.html.index(false))
    }
    val successFunction = { (userSignup: UserSignup) =>
      val possibleUser = userDao.getUser(userSignup.username)
      possibleUser match
        case Some(u) =>
          Redirect(s"${routes.IndexController.index().toString}?showLogin=false")
            .flashing("SignupError" -> "Username is already taken.")
        case _ if userSignup.password != userSignup.repeatPassword =>
          Redirect(s"${routes.IndexController.index().toString}?showLogin=false")
            .flashing("SignupError" -> "The passwords are not equal.")
        case _ =>
          userDao.createUser(userSignup.username, userSignup.password)
          Redirect(routes.IndexController.index())
            .flashing("LoginInfo" -> "Please login.")
    }
    formValidationResult.fold(
      errorFunction,
      successFunction
    )
  }

  def logout() = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.IndexController.index()).withNewSession
  }
}

object LoginForm:
  val form: Form[User] = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
    )(User.apply)(User.reverseApply)
  )
  val formSubmitUrl = routes.LoginController.login()

object SignupForm:
  val form: Form[UserSignup] = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText,
    )(UserSignup.apply)(UserSignup.reverseApply)
  )
  val formSubmitUrl = routes.LoginController.signup()
