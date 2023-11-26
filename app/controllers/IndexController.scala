package controllers

import models.Global

import javax.inject.*
import play.api.*
import play.api.mvc.*

@Singleton
class IndexController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    (request.session.get(Global.SESSION_USERNAME_KEY), request.getQueryString("showLogin")) match
      case (Some(_), _) => Redirect(routes.HomeController.index())
      case (_, Some(param)) =>
        Ok(views.html.index(param=="true"))
      case _ => Ok(views.html.index(true))
  }
}
