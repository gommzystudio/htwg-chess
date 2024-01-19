package model.moves.special

import model.moves.MoveValidator
import model.pieces.{Piece, Pawn}
import model.position.{PositionBaseImpl, PositionInterface}
import model.field.FieldInterface
import util.color.Color

// PawnFirstMoveValidator determines the valid first moves for a Pawn piece in chess.
class PawnFirstMoveValidator extends MoveValidator {

  // Calculates valid first moves for a Pawn based on its current position and the state of the field.
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Pawn]) // Ensures that the piece is a Pawn.

    // Direction and start position are set based on the Pawn's color.
    val direction = piece.color match {
      case Color.White => 1 // Moves up for White.
      case Color.Black => -1 // Moves down for Black.
    }
    val startPosition = piece.color match {
      case Color.White => 2 // Start row for White Pawn.
      case Color.Black => 7 // Start row for Black Pawn.
    }

    // Calculates the position for a two-square advance.
    val newPosition =
      PositionBaseImpl(position.getX(), position.getY() + 2 * direction)
    // Intermediate position for checking if the path is clear.
    val skipPosition =
      PositionBaseImpl(position.getX(), position.getY() + direction)

    // Check if the Pawn is at the start position and the path is clear for a two-square move.
    if (
      position.getY() == startPosition && field.getPiece(newPosition).isEmpty &&
      field.getPiece(skipPosition).isEmpty && field.isPositionValid(newPosition)
    ) {
      callNextMoveValidator(piece, position, field, newPosition :: moves)
    } else {
      callNextMoveValidator(piece, position, field, moves)
    }
  }
}
