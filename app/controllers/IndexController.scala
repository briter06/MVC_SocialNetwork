package controllers

import models.Global
import javax.inject.*
import play.api.*
import play.api.mvc.*

/**
 * Controller to show the index page to login or signup
 * @param controllerComponents - Injected controller components
 */
@Singleton
class IndexController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Route for the index page to login or signup
   * @return - New action to show the forms
   */
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    (request.session.get(Global.SESSION_USERNAME_KEY), request.getQueryString("showLogin")) match
      case (Some(_), _) => Redirect(routes.HomeController.index())
      case (_, Some(param)) =>
        Ok(views.html.index(param=="true"))
      case _ => Ok(views.html.index(true))
  }
}
