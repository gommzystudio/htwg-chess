package model.pieces

import model.position.PositionInterface
import model.field.FieldInterface
import model.pieces.Piece
import util.color.Color

final case class PieceMockImpl(c: Color) extends Piece(c) {
  override def getSymbol(): String = {
    return "♟"
  }

  override def whiteAvailableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    return List()
  }
}
