package model.pieces

import model.position.PositionInterface
import model.field.FieldInterface
import model.pieces.Piece

class Rook(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♖" else "♜"
  }

  override def whiteAvailableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    return List()
  }
}
