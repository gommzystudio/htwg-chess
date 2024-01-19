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

// PawnStandardMoveValidator is responsible for determining the standard forward moves of a Pawn piece in chess.
class PawnStandardMoveValidator extends MoveValidator {

  // Calculates valid standard forward moves for a Pawn based on its current position and the state of the field.
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Pawn]) // Ensures that the piece is a Pawn.

    // Sets the direction of movement based on the Pawn's color.
    val direction = piece.color match {
      case Color.White => 1 // Moves up for White.
      case Color.Black => -1 // Moves down for Black.
    }

    // Calculates the new position for a single square forward move.
    val newPosition =
      PositionBaseImpl(position.getX(), position.getY() + direction)

    // Checks if the new position is valid and unoccupied.
    if (
      field.getPiece(newPosition).isEmpty && field.isPositionValid(newPosition)
    ) {
      callNextMoveValidator(piece, position, field, newPosition :: moves)
    } else {
      callNextMoveValidator(piece, position, field, moves)
    }
  }
}
