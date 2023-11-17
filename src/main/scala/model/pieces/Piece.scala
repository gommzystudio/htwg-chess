package model.pieces

import model.Position
import model.Field

enum Color:
  case Black, White;

abstract class Piece(c: Color) {
  val color: Color = c;

  def availableMoves(position: Position, field: Field): List[Position] = {
    if (c == Color.White) {
      return whiteAvailableMoves(position, field)
    } else {
      return whiteAvailableMoves(position.flipPosition(), field.flipBoard())
        .map(
          _.flipPosition()
        );
    }
  }

  def whiteAvailableMoves(position: Position, field: Field): List[Position]

  def getSymbol(): String
}
