package services

import models.Tree

import scala.util.parsing.combinator.Parsers

trait ParserComponents {

  val parser: Parser

  trait Parser extends Parsers {

    def parse(expression: String): ParseResult[Tree]

  }

}
