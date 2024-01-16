package model.moves.special

import model.moves.MoveValidator
import model.pieces.{Piece, Pawn}
import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}
import util.color.Color

class PawnCaptureMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Pawn])

    val captureDirections = piece.color match {
      case Color.White => List((1, 1), (-1, 1))
      case Color.Black => List((1, -1), (-1, -1))
    }

    val newMoves = for {
      (dx, dy) <- captureDirections
      newPos = PositionBaseImpl(position.getX() + dx, position.getY() + dy)
      if field.getPiece(newPos).exists(_.color != piece.color) && field
        .isPositionValid(newPos)
    } yield newPos

    return callNextMoveValidator(piece, position, field, moves ++ newMoves)
  }
}
