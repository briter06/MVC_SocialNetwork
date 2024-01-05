package helpers

import javax.inject.Singleton
import scala.concurrent.*
import play.api.http.HttpErrorHandler
import play.api.mvc.*
import play.api.mvc.Results.*
import play.api.Logger

/**
 * Custom Error Handlers
 */
@Singleton
class ErrorHandler extends HttpErrorHandler {

  /**
   * Private logger instances
   */
  private val logger = Logger(this.getClass)

  /**
   * Handle client errors
   * @param request - Error request
   * @param statusCode - Status of the request
   * @param message - Message of the error
   * @return - Action for the error
   */
  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    logger.error(s"Client Error: ${statusCode} - ${request.path} - ${message}")
    Future.successful(
      Status(statusCode)(views.html.error(statusCode.toString)(request.session))
    )
  }

  /**
   * Handle server errors
   * @param request - Error request
   * @param exception - Exception of the error
   * @return - Action for the error
   */
  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    logger.error(s"Internal Server Error: 500 - ${exception.getMessage}")
    Future.successful(
      InternalServerError(views.html.error("500")(request.session))
    )
  }
}