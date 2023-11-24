package model.moves.special

import model.moves.MoveValidator
import model.pieces.Piece
import model.Position
import model.Field
import scala.collection.mutable.ArrayBuffer
import model.pieces.Pawn

class PawnCaptureMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: Position,
      field: Field,
      moves: List[Position]
  ): List[Position] = {
    assert(piece.isInstanceOf[Pawn])

    val newMoves = ArrayBuffer[Position]()
    val leftPosition = Position(position.x - 1, position.y + 1)
    val rightPosition = Position(position.x + 1, position.y + 1)
    if (
      field.getPiece(leftPosition.x, leftPosition.y) != None && field
        .getPiece(leftPosition.x, leftPosition.y)
        .get
        .color != piece.color
    ) {
      newMoves += leftPosition
    }
    if (
      field.getPiece(rightPosition.x, rightPosition.y) != None && field
        .getPiece(rightPosition.x, rightPosition.y)
        .get
        .color != piece.color
    ) {
      newMoves += rightPosition
    }
    return callNextMoveValidator(piece, position, field, newMoves.toList)
  }
}
