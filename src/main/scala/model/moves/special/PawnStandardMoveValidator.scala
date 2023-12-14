package model.moves.special

import model.moves.MoveValidator
import model.pieces.Piece
import model.position.PositionInterface
import model.position.PositionBaseImpl
import model.field.FieldInterface
import model.gamestate.GameStateInterface
import scala.collection.mutable.ArrayBuffer
import model.pieces.Pawn

class PawnStandardMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Pawn])

    var newMoves = moves

    val newPosition = PositionBaseImpl(position.getX(), position.getY() + 1)
    if (field.getPiece(newPosition.getX(), newPosition.getY()) == None) {
      newMoves = newPosition :: newMoves
    }

    return callNextMoveValidator(piece, position, field, newMoves)
  }
}
