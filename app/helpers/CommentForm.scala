package helpers

import controllers.routes
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}

object CommentForm:
  val form: Form[CommentFormResponse] = Form(
    mapping(
      "postId" -> nonEmptyText,
      "comment" -> nonEmptyText
        .verifying("must have at most 100 characters", s => s.length <= 100),
    )(CommentFormResponse.apply)(CommentFormResponse.reverseApply)
  )
  val formSubmitUrl = routes.DetailsController.createComment()

case class CommentFormResponse(postId: String, comment: String)

object CommentFormResponse:
  def reverseApply(commentFormResponse: CommentFormResponse): Option[(String, String)] =
    Some(commentFormResponse.postId, commentFormResponse.comment)