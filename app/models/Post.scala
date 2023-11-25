package models

case class Post (id: String, username: String, photoPath: String, creationDate: String, description: String, likes: List[String], var comments: List[Comment]):
  def likesNumber: Int = likes.length
