package models

/**
 * Comment model
 * @param username - Username that created the comment
 * @param comment - String with the comment
 */
case class Comment(username: String, comment: String)