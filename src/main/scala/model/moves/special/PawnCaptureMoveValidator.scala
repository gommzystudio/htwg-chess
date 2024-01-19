package model.moves.special

import model.moves.MoveValidator
import model.pieces.{Piece, Pawn}
import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}
import util.color.Color

// PawnCaptureMoveValidator is responsible for determining the capture moves of a Pawn piece in chess.
class PawnCaptureMoveValidator extends MoveValidator {

  // Calculates valid capture moves for a Pawn based on its current position and the state of the field.
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Pawn]) // Ensures that the piece is a Pawn.

    // Defines capture directions based on the Pawn's color.
    val captureDirections = piece.color match {
      case Color.White =>
        List((1, 1), (-1, 1)) // Diagonal forward moves for White Pawn.
      case Color.Black =>
        List((1, -1), (-1, -1)) // Diagonal forward moves for Black Pawn.
    }

    // Calculates new positions for capture moves.
    val newMoves = for {
      (dx, dy) <- captureDirections
      newPos = PositionBaseImpl(position.getX() + dx, position.getY() + dy)
      if field.getPiece(newPos).exists(_.color != piece.color) && field
        .isPositionValid(newPos)
    } yield newPos

    // Includes these new capture moves with existing ones, passing to the next move validator if applicable.
    callNextMoveValidator(piece, position, field, moves ++ newMoves)
  }
}
