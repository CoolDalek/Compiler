package services.impls

import models.Tree
import models.Tree._
import services.ParserComponents

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

trait ParserComponentsImpl extends ParserComponents {

  override val parser: Parser = MathGrammar

  private object MathGrammar extends Parser with RegexParsers {

    def parse(expression: String): ParseResult[Tree] = parseAll(expr, expression)

    def literal: Parser[Tree] = number ^^ Leaf | "(" ~> expr <~ ")"

    def expr: Parser[Tree] = leftExpr | literal

    def leftExpr: Parser[Tree] = literal ~ rep1(leftOps ~ expr) ^^ {
      case left ~ list =>
        list.foldLeft(left) {
          case (acc, op ~ expr) =>
            op.setChild(acc, expr)
        }
    }

    def leftOps: Parser[Tree] = ("*" | "/" | "%") ^^ {Node(_)} | lowPriorityLeftOps

    def lowPriorityLeftOps: Parser[Tree] = ("+" | "-") ^^ {LowPriorityNode(_)}

    val signedByte: Regex = """-1[0-2][0-7]|1[0-2][0-8]|-?\d{1,2}""".r

    val number: Regex = signedByte

  }

}
