package model.pieces

import model.Position
import model.Field

enum Color:
  case Black, White;

case class Piece(c: Color) {
  val color: Color = c;

  def availableMoves(position: Position, field: Field): List[Position] = {
    throw NotImplementedError()
  }

  def getSymbol(): String = {
    throw NotImplementedError()
  }
}