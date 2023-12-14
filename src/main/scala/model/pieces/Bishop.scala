package model.pieces

import model.field.FieldInterface
import model.position.PositionInterface
import model.pieces.Piece

class Bishop(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♗" else "♝";
  }

  override def whiteAvailableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    return List()
  }
}
