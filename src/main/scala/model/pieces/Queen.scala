package model.pieces

import model.position.PositionInterface
import model.field.FieldInterface
import model.pieces.Piece
import model.moves.StraightMoveValidator
import model.moves.DiagonalMoveValidator
import util.color.Color

class Queen(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♕" else "♛"
  }

  override def whiteAvailableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    val straightMoveValidator = new StraightMoveValidator()
    val diagonalMoveValidator = new DiagonalMoveValidator()

    straightMoveValidator.setNext(diagonalMoveValidator)

    return straightMoveValidator.getValidMoves(
      this,
      position,
      field,
      List()
    )
  }
}
