package models

case class User(username: String, password: String)

object User:
  def reverseApply(user: User): Option[(String, String)] =
    Some(user.username, user.password)