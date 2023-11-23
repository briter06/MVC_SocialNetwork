package controllers

import models.Global
import play.api.*
import play.api.mvc.*

import javax.inject.*

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    request.session.get(Global.SESSION_USERNAME_KEY) match
      case Some(_) => Ok(views.html.home())
      case _ => Redirect(routes.IndexController.index())
  }
}
