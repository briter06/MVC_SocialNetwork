package controllers

import models.{Global, User, UserDao}
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
      BadRequest(views.html.index(LoginForm.form, LoginForm.formSubmitUrl))
    }
    val successFunction = { (user: User) =>
      val possibleUser = userDao.getUser(user.username)
      possibleUser match
        case Some(u) if u.password == user.password =>
          Redirect(routes.HomeController.index())
            .withSession(Global.SESSION_USERNAME_KEY -> user.username)
        case _ => Redirect(routes.IndexController.index())
          .flashing("Error" -> "Invalid username/password.")
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
