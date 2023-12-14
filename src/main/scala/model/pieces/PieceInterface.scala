package model.pieces

import model.position.PositionInterface
import model.field.FieldInterface
import java.io.StringReader
import model.gamestate.GameStateInterface

enum Color:
  case Black, White;

abstract trait Piece(c: Color) {
  val color: Color = c;

  def availableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    if (c == Color.White) {
      return whiteAvailableMoves(position, field)
    } else {
      return whiteAvailableMoves(position.flipPosition(), field.flipBoard())
        .map(
          _.flipPosition()
        );
    }
  }

  def whiteAvailableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface]

  def getSymbol(): String
}
