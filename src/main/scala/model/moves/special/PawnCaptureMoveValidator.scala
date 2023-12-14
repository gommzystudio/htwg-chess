package model.moves.special

import model.moves.MoveValidator
import model.pieces.Piece
import model.field.FieldInterface
import model.position.PositionBaseImpl
import model.position.PositionInterface
import scala.collection.mutable.ArrayBuffer
import model.pieces.Pawn

class PawnCaptureMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Pawn])

    var newMoves = moves

    val leftPosition =
      PositionBaseImpl(position.getX() - 1, position.getY() + 1)
    val rightPosition =
      PositionBaseImpl(position.getX() + 1, position.getY() + 1)
    if (
      field.getPiece(leftPosition.x, leftPosition.y) != None && field
        .getPiece(leftPosition.x, leftPosition.y)
        .get
        .color != piece.color
    ) {
      newMoves = leftPosition :: newMoves
    }
    if (
      field.getPiece(rightPosition.x, rightPosition.y) != None && field
        .getPiece(rightPosition.x, rightPosition.y)
        .get
        .color != piece.color
    ) {
      newMoves = rightPosition :: newMoves
    }
    return callNextMoveValidator(piece, position, field, newMoves)
  }
}
