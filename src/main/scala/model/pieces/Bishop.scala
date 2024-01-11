package model.pieces

import model.field.FieldInterface
import model.position.PositionInterface
import model.moves.DiagonalMoveValidator
import util.color.Color

class Bishop(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♗" else "♝";
  }

  override def whiteAvailableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    val diagonalMoveValidator = new DiagonalMoveValidator()

    return diagonalMoveValidator.getValidMoves(
      this,
      position,
      field,
      List()
    )
  }
}
