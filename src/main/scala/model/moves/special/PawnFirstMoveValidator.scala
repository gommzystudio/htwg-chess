package model.moves.special

import model.moves.MoveValidator
import model.pieces.{Piece, Pawn}
import model.position.{PositionBaseImpl, PositionInterface}
import model.field.FieldInterface
import util.color.Color

class PawnFirstMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Pawn])

    val direction = piece.color match {
      case Color.White => 1
      case Color.Black => -1
    }
    val startPosition = piece.color match {
      case Color.White => 2
      case Color.Black => 7
    }

    val newPosition =
      PositionBaseImpl(position.getX(), position.getY() + 2 * direction)
    val skipPosition =
      PositionBaseImpl(position.getX(), position.getY() + direction)

    if (
      position.getY() == startPosition && field.getPiece(newPosition).isEmpty &&
      field.getPiece(skipPosition).isEmpty && field.isPositionValid(newPosition)
    ) {
      return callNextMoveValidator(piece, position, field, newPosition :: moves)
    } else {
      return callNextMoveValidator(piece, position, field, moves)
    }
  }
}
