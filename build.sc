import mill._
import $ivy.`com.lihaoyi::mill-contrib-playlib:`,  mill.playlib._

object mvc_socialnetwork extends PlayModule with SingleModule {

  def scalaVersion = "3.3.1"
  def playVersion = "2.9.0"
  def twirlVersion = "1.6.2"

  object test extends PlayTests
}
