package model.moves.special

import model.moves.MoveValidator
import model.pieces.Piece
import model.position.PositionBaseImpl
import model.position.PositionInterface
import model.field.FieldInterface
import scala.collection.mutable.ArrayBuffer
import model.pieces.Pawn

class PawnFirstMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Pawn])

    var newMoves = moves

    val newPosition = PositionBaseImpl(position.getX(), position.getY() + 2)
    val skipPosition = PositionBaseImpl(position.getX(), position.getY() + 1)
    if (
      field.getPiece(newPosition.getX(), newPosition.getY()) == None &&
      field.getPiece(skipPosition.getX(), skipPosition.getY()) == None &&
      position.getY() == 2
    ) {
      newMoves = newPosition :: newMoves
    }
    return callNextMoveValidator(piece, position, field, newMoves)
  }
}
