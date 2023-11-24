package model.pieces

import model.Position
import model.Field

class Bishop(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♗" else "♝";
  }

  override def whiteAvailableMoves(
      position: Position,
      field: Field
  ): List[Position] = {
    return List()
  }
}
