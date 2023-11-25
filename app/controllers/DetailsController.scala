package controllers

import models.{Comment, CommentFormResponse, Global, PostDao}
import play.api.*
import play.api.mvc.*
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}

import javax.inject.*

@Singleton
class DetailsController @Inject()(val controllerComponents: ControllerComponents, val postDao: PostDao) extends BaseController {

  def index(id: String) = Action { implicit request: Request[AnyContent] =>
    request.session.get(Global.SESSION_USERNAME_KEY) match
      case Some(_) =>
        val result = postDao.getPost(id)
        result match
          case Some(post) => Ok(views.html.details(post)(CommentForm.form, CommentForm.formSubmitUrl))
          case _ => Redirect(routes.IndexController.index())
      case _ => Redirect(routes.IndexController.index())
  }

  def createComment() = Action { implicit request: Request[AnyContent] =>
    val formValidationResult: Form[CommentFormResponse] = CommentForm.form.bindFromRequest()
    val errorFunction = { (formWithErrors: Form[CommentFormResponse]) =>
      Redirect(routes.HomeController.index())
    }
    val successFunction = { (comment: CommentFormResponse) =>
      val result = postDao.getPost(comment.postId)
      (result, request.session.get(Global.SESSION_USERNAME_KEY)) match
        case (Some(post), Some(username)) =>
          post.comments = Comment(username, comment.comment) :: post.comments
          Redirect(routes.DetailsController.index(comment.postId))
        case _ => Redirect(routes.HomeController.index())
    }
    formValidationResult.fold(
      errorFunction,
      successFunction
    )
  }
}

object CommentForm:
  val form: Form[CommentFormResponse] = Form(
    mapping(
      "postId" -> nonEmptyText,
      "comment" -> nonEmptyText,
    )(CommentFormResponse.apply)(CommentFormResponse.reverseApply)
  )
  val formSubmitUrl = routes.DetailsController.createComment()