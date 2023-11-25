package controllers

import models.{Global, PostDao}
import play.api.*
import play.api.mvc.*

import javax.inject.*

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, val postDao: PostDao) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    request.session.get(Global.SESSION_USERNAME_KEY) match
      case Some(_) =>
        val sortBy = request.getQueryString("sortBy") match
          case Some(value) if value == "date" || value == "likes" => value
          case _ => "date"
        val asc = request.getQueryString("asc") match
          case Some(value) => value == "true"
          case _ => false
        val posts = postDao.getPosts(sortBy, asc)
        Ok(views.html.home(posts, sortBy, asc))
      case _ => Redirect(routes.IndexController.index())
  }
}
