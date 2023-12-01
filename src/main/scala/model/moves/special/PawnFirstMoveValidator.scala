package model.moves.special

import model.moves.MoveValidator
import model.pieces.Piece
import model.Position
import model.Field
import model.GameState
import scala.collection.mutable.ArrayBuffer
import model.pieces.Pawn

class PawnFirstMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: Position,
      field: Field,
      moves: List[Position]
  ): List[Position] = {
    assert(piece.isInstanceOf[Pawn])

    var newMoves = moves

    val newPosition = Position(position.x, position.y + 2)
    val skipPosition = Position(position.x, position.y + 1)
    if (
      field.getPiece(newPosition.x, newPosition.y) == None &&
      field.getPiece(skipPosition.x, skipPosition.y) == None &&
      position.y == 2
    ) {
      newMoves = newPosition :: newMoves
    }
    return callNextMoveValidator(piece, position, field, newMoves)
  }
}
