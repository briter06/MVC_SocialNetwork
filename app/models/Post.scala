package models

case class Post (id: Int, username: String, photoPath: String, creationDate: String, description: String, likes: List[String], comments: List[Comment]):
  def likesNumber: Int = likes.length
