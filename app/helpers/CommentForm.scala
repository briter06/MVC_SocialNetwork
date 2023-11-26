package helpers

import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}

/**
 * Object to wrap the comment form
 */
object CommentForm:
  val form: Form[CommentFormResponse] = Form(
    mapping(
      "comment" -> nonEmptyText
        .verifying("must have at most 100 characters", s => s.length <= 100),
    )(CommentFormResponse.apply)(CommentFormResponse.reverseApply)
  )


/**
 * Case class representing the structure of the response of the comment form
 * @param comment - String with the comment
 */
case class CommentFormResponse(comment: String)

/**
 * Companion object to implement the reverse apply
 */
object CommentFormResponse:
  def reverseApply(commentFormResponse: CommentFormResponse): Option[(String)] =
    Some(commentFormResponse.comment)