package models

/**
 * Post model
 * @param id - Id of the post
 * @param username - Username that created the post
 * @param photoPath - Path of the uploaded photo
 * @param creationDate - Date when the post was created
 * @param description - Description of the post
 * @param likes - List with all the users that have liked the post
 * @param comments - List of all the comments of the post
 */
case class Post (id: String, username: String, photoPath: String, creationDate: String, description: String, var likes: List[String], var comments: List[Comment]):

  /**
   * Number of likes the post has
   * @return - Number of likes
   */
  def likesNumber: Int = likes.length

  /**
   * Evaluates if an specific user has liked the post
   * @param usernameOption - Option with the username
   * @return - True if the user has liked the post. Otherwise, false
   */
  def userLiked(usernameOption: Option[String]): Boolean =
    usernameOption match
      case Some(username) => userLiked(username)
      case _ => false

  /**
   * Evaluates if an specific user has liked the post
   *
   * @param username - Username of the user
   * @return - True if the user has liked the post. Otherwise, false
   */
  def userLiked(username: String): Boolean =
    likes.contains(username)
