package controllers

import models.{Global, PostDao}
import play.api.*
import play.api.mvc.*
import javax.inject.*

/**
 * Controller to show the home page with all the posts
 * @param controllerComponents - Injected controller components
 * @param postDao - Injected DAO of the Post model
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, val postDao: PostDao) extends BaseController {

  /**
   * Private logger instances
   */
  private val logger = Logger(this.getClass)

  /**
   * Route for the index page to show all the posts
   * @return - New action to show all the posts
   */
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    request.session.get(Global.SESSION_USERNAME_KEY) match
      case Some(_) =>
        val sortBy = request.getQueryString("sortBy") match
          case Some(value) if value == "date" || value == "likes" => value
          case _ => "date"
        val asc = request.getQueryString("asc") match
          case Some(value) => value == "true"
          case _ => false
        val posts = postDao.getPosts(sortBy, asc)
        logger.info(s"Index access. Sort by: ${sortBy}, Asc: ${asc}")
        Ok(views.html.home(posts, sortBy, asc))
      case _ => Redirect(routes.IndexController.index())
  }
}
