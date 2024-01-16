package model.pieces

import model.position.PositionInterface
import model.field.FieldInterface
import java.io.StringReader
import model.gamestate.GameStateInterface
import util.color.Color

trait Piece(c: Color) {
  val color: Color = c;

  def availableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface]

  def getSymbol(): String
}
