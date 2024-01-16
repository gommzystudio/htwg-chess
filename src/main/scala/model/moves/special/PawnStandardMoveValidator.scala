package model.moves.special

import model.moves.MoveValidator
import model.pieces.Piece
import model.position.PositionInterface
import model.position.PositionBaseImpl
import model.field.FieldInterface
import model.gamestate.GameStateInterface
import scala.collection.mutable.ArrayBuffer
import model.pieces.Pawn
import util.color.Color

class PawnStandardMoveValidator extends MoveValidator {
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

    val newPosition =
      PositionBaseImpl(position.getX(), position.getY() + direction)

    if (
      field.getPiece(newPosition).isEmpty && field.isPositionValid(newPosition)
    ) {
      return callNextMoveValidator(piece, position, field, newPosition :: moves)
    } else {
      return callNextMoveValidator(piece, position, field, moves)
    }
  }
}
