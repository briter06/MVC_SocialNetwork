package models

case class Comment(username: String, comment: String)

case class CommentFormResponse(postId: String, comment: String)

object CommentFormResponse:
  def reverseApply(commentFormResponse: CommentFormResponse): Option[(String, String)] =
    Some(commentFormResponse.postId, commentFormResponse.comment)
