package model.moves.special

import model.moves.MoveValidator
import model.pieces.Piece
import model.Position
import model.Field
import scala.collection.mutable.ArrayBuffer
import model.pieces.Pawn

class PawnStandardMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: Position,
      field: Field,
      moves: List[Position]
  ): List[Position] = {
    assert(piece.isInstanceOf[Pawn])

    val newMoves = ArrayBuffer[Position]()
    val newPosition = Position(position.x, position.y + 1)
    if (field.getPiece(newPosition.x, newPosition.y) == None) {
      newMoves += newPosition
    }
    return callNextMoveValidator(piece, position, field, newMoves.toList)
  }
}
