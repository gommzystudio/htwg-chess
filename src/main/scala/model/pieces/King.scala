package model.pieces

import model.field.FieldInterface
import model.position.PositionInterface
import model.pieces.Piece
import model.moves.StraightMoveValidator
import model.moves.DiagonalMoveValidator

class King(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♔" else "♚"
  }

  override def whiteAvailableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    val straightMoveValidator = new StraightMoveValidator(dist = 1)
    val diagonalMoveValidator = new DiagonalMoveValidator(dist = 1)

    straightMoveValidator.setNext(diagonalMoveValidator)

    return straightMoveValidator.getValidMoves(
      this,
      position,
      field,
      List()
    )
  }
}
