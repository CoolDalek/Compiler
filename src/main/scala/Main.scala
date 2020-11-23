import services.impls._

object Main {

  val app = new AnyRef with ParserComponentsImpl with UIComponentsImpl

  def main(args: Array[String]): Unit = {
    app.ui.start()
  }



}