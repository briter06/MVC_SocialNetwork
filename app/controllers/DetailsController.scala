package controllers

import helpers.{CommentForm, CommentFormResponse}
import models.{Comment, Global, PostDao}
import play.api.*
import play.api.mvc.*
import play.api.data.Form
import play.api.libs.json.Json

import javax.inject.*

/**
 * Controller to show the details of a post
 * @param controllerComponents - Injected controller components
 * @param postDao - Injected DAO of the Post model
 */
@Singleton
class DetailsController @Inject()(val controllerComponents: ControllerComponents, val postDao: PostDao) extends BaseController {

  /**
   * Private logger instances
   */
  private val logger = Logger(this.getClass)

  /**
   * Route for the index page to show the details of a post
   * @param id - Id of the post to show
   * @return - New action to show the post's details
   */
  def index(id: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    request.session.get(Global.SESSION_USERNAME_KEY) match
      case Some(_) =>
        val result = postDao.getPost(id)
        result match
          case Some(post) =>
            logger.info(s"Index access for post ${id}")
            Ok(views.html.details(post))
          case _ => NotFound(views.html.error("404"))
      case _ => Redirect(routes.IndexController.index())
  }

  /**
   * POST route to process the form and create a new comment on the post
   * @return - New action after creating the comment
   */
  def createComment(id: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    CommentForm.form.bindFromRequest().fold(
      (formWithErrors: Form[CommentFormResponse]) =>
        val values: Seq[(String, String)] = formWithErrors.errors.map(
          error => ("Error", s"The field '${error.key}' ${error.message}")
        )
        logger.error("CommentForm error")
        Redirect(routes.HomeController.index()).flashing(values: _*),
      (comment: CommentFormResponse) =>
        (postDao.getPost(id), request.session.get(Global.SESSION_USERNAME_KEY)) match
          case (Some(post), Some(username)) =>
            post.comments = Comment(username, comment.comment) :: post.comments
            logger.info(s"New comment created by ${username} for post ${id}")
            Redirect(routes.DetailsController.index(post.id))
          case _ => Redirect(routes.HomeController.index())
    )
  }

  /**
   * POST route to process the form and register a new like on the post
   * @return - New action after registering the like
   */
  def likePost(id: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    (postDao.getPost(id), request.session.get(Global.SESSION_USERNAME_KEY)) match
      case (Some(post), Some(username)) =>
        if (post.likes.contains(username)) {
          post.likes = post.likes.filterNot(u => u == username)
          logger.info(s"User ${username} removed a like for post ${post.id}")
        } else {
          post.likes = username :: post.likes
          logger.info(s"User ${username} liked post ${post.id}")
        }
        val result = Json.obj(
          "numLikes" -> post.likes.length
        )
        Ok(result)
      case _ =>
        val result = Json.obj(
          "error" -> "server_error"
        )
        Ok(result)
  }
}