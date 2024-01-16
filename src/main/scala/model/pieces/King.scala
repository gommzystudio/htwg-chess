package model.pieces

import model.field.FieldInterface
import model.position.PositionInterface
import model.moves.StraightMoveValidator
import model.moves.DiagonalMoveValidator
import util.color.Color

class King(c: Color) extends Piece(c) {
  override def getSymbol() = if (c == Color.White) "♔" else "♚"

  override def availableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    val straightMoveValidator = new StraightMoveValidator(dist = 1)
    val diagonalMoveValidator = new DiagonalMoveValidator(dist = 1)

    straightMoveValidator.next(diagonalMoveValidator)

    return straightMoveValidator.getValidMoves(
      this,
      position,
      field,
      List()
    )
  }
}
