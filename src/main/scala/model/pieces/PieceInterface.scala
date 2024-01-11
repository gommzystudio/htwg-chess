package model.pieces

import model.position.PositionInterface
import model.field.FieldInterface
import java.io.StringReader
import model.gamestate.GameStateInterface
import util.color.Color

abstract trait Piece(c: Color) {
  val color: Color = c;

  def availableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    if (field.getCurrentPlayer() != c) {
      return List()
    }

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
