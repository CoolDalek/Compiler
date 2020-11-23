package services.impls

import services.{ParserComponents, UIComponents}

import scala.io.StdIn

trait UIComponentsImpl extends UIComponents {

  this: ParserComponents =>

  override val ui: UI = CMD

  private object CMD extends UI {

    override def start(): Unit = {

      def printInstruction(): Unit = {
        println("Write \"exit\" to exit.")
        println("Write math expression to build tree.")
      }

      @scala.annotation.tailrec
      def readUserInput(): Unit = {
        StdIn.readLine() match {
          case "exit" =>
            System.exit(0)
          case expression =>
            val result = parser.parse(expression)
            println(result)
            readUserInput()
        }
      }

      printInstruction()
      readUserInput()
    }

  }

}
