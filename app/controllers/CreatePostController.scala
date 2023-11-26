package controllers

import models.{Global, PostDao}
import play.api.*
import play.api.mvc.*
import play.api.libs.Files
import java.nio.file.Paths
import javax.inject.*

/**
 * Controller to create a new post
 * @param controllerComponents - Injected controller components
 * @param postDao - Injected DAO of the Post model
 */
@Singleton
class CreatePostController @Inject()(val controllerComponents: ControllerComponents, val postDao: PostDao) extends BaseController {

  /**
   * Private logger instances
   */
  private val logger = Logger(this.getClass)

  /**
   * Route for the index page to create a post
   * @return - New action for the user
   */
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    request.session.get(Global.SESSION_USERNAME_KEY) match
      case Some(_) =>
        logger.info("Index access")
        Ok(views.html.createPost())
      case _ => Redirect(routes.IndexController.index())
  }

  /**
   * POST route to process the form and create a new post and upload a file
   * @return - New action after creating the post and upload the file
   */
  def createPost(): Action[MultipartFormData[Files.TemporaryFile]] = Action(parse.multipartFormData) { implicit request:  Request[MultipartFormData[Files.TemporaryFile]] =>
    val sessionUser = request.session.get(Global.SESSION_USERNAME_KEY)
    val requestFile = request.body.file("image")
    val requestDescription = request.body.dataParts.get("description")
    (sessionUser, requestFile, requestDescription) match
      case (Some(username), Some(multipartData), Some(Seq(description))) =>
        if(multipartData.fileSize.toInt < 10485760){
          val extension = Paths.get(multipartData.filename).getFileName.toString.split("\\.").last
          val filename = System.currentTimeMillis().toString
          val basePath = s"images/uploads/$filename.$extension"
          multipartData.ref.copyTo(Paths.get(s"./public/$basePath"), replace = true)
          postDao.createPost(filename, username, routes.Assets.versioned(basePath).toString, description)
          logger.info(s"New post created by ${username} with id ${filename}")
          Redirect(routes.HomeController.index())
        }else{
          logger.error("File is too big")
          Redirect(routes.CreatePostController.index()).flashing("Error" -> "File is too big")
        }
      case _ =>
        logger.error("Error uploading the file")
        Redirect(routes.CreatePostController.index()).flashing("Error" -> "Error uploading the file")
  }
}