package services.impls

import models.Tree
import models.Tree._
import services.ParserComponents

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

trait ParserComponentsImpl extends ParserComponents {

  override val parser: Parser = MathGrammar

  private object MathGrammar extends Parser with RegexParsers {

    override def parse(input: String): ParseResult[Tree] = parseAll(expression, input)

    def literal: Parser[Tree] = number | "(" ~> expression <~ ")"

    def number: Parser[Tree] = signedByte ^^ Leaf

    val signedByte: Regex = """-1[0-2][0-7]|1[0-2][0-8]|-?\d{1,2}""".r

    def expression: Parser[Tree] = leftExpr | literal

    def leftExpr: Parser[Tree] = literal ~ rep1(leftOps ~ expression) ^^ {
      case left ~ list =>
        list.foldLeft(left) {
          case (acc, op ~ expr) =>
            op.setChild(acc, expr)
        }
    }

    def leftOps: Parser[Tree] = highPriorityLeftOps | lowPriorityLeftOps

    def highPriorityLeftOps: Parser[Tree] = ("*" | "/" | "%") ^^ {Node(_)}

    def lowPriorityLeftOps: Parser[Tree] = ("+" | "-") ^^ {LowPriorityNode(_)}

  }

}
