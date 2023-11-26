package models

case class User(username: String, password: String)

object User:
  def reverseApply(user: User): Option[(String, String)] =
    Some(user.username, user.password)


case class UserSignup(username: String, password: String, repeatPassword: String)

object UserSignup:
  def reverseApply(user: UserSignup): Option[(String, String, String)] =
    Some(user.username, user.password, user.repeatPassword)