package models

case class Post (id: String, username: String, photoPath: String, creationDate: String, description: String, likes: List[String], var comments: List[Comment]):
  def likesNumber: Int = likes.length

  def userLiked(usernameOption: Option[String]): Boolean =
    usernameOption match
      case Some(username) => userLiked(username)
      case _ => false

  def userLiked(username: String): Boolean =
    likes.contains(username)
