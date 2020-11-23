package services

trait UIComponents {

  val ui: UI

  trait UI {

    def start(): Unit

  }

}
