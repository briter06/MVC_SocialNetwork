package controllers

import helpers.{CommentForm, CommentFormResponse}
import models.{Comment, Global, PostDao}
import play.api.*
import play.api.mvc.*
import play.api.data.Form

import javax.inject.*

@Singleton
class DetailsController @Inject()(val controllerComponents: ControllerComponents, val postDao: PostDao) extends BaseController {

  def index(id: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    request.session.get(Global.SESSION_USERNAME_KEY) match
      case Some(_) =>
        val result = postDao.getPost(id)
        result match
          case Some(post) => Ok(views.html.details(post))
          case _ => Redirect(routes.IndexController.index())
      case _ => Redirect(routes.IndexController.index())
  }

  def createComment(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    CommentForm.form.bindFromRequest().fold(
      (formWithErrors: Form[CommentFormResponse]) =>
        val values: Seq[(String, String)] = formWithErrors.errors.map(
          error => ("Error", s"The field '${error.key}' ${error.message}")
        )
        Redirect(routes.HomeController.index()).flashing(values: _*),
      (comment: CommentFormResponse) =>
        val result = postDao.getPost(comment.postId)
        (result, request.session.get(Global.SESSION_USERNAME_KEY)) match
          case (Some(post), Some(username)) =>
            post.comments = Comment(username, comment.comment) :: post.comments
            Redirect(routes.DetailsController.index(comment.postId))
          case _ => Redirect(routes.HomeController.index())
    )
  }
}