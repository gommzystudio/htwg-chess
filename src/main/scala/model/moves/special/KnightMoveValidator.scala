package model.moves.special

import model.moves.MoveValidator
import model.pieces.{Piece, Knight}
import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}

// KnightMoveValidator is responsible for determining the valid moves of a Knight piece in chess.
class KnightMoveValidator extends MoveValidator {

  // Calculates valid moves for a Knight based on its current position and the state of the field.
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Knight]) // Ensures that the piece is a Knight.

    // Directions a Knight can move: L-shapes in each of the 8 possible directions.
    val directions = List(
      (-1, 2),
      (1, 2),
      (-2, 1),
      (-2, -1),
      (-1, -2),
      (1, -2),
      (2, 1),
      (2, -1)
    )

    // Calculates new positions based on the Knight's movement pattern.
    val newMoves = for {
      (dx, dy) <- directions
      newPos = PositionBaseImpl(position.getX() + dx, position.getY() + dy)
      if field.isPositionValid(newPos) && (field
        .getPiece(newPos)
        .isEmpty || field.getPiece(newPos).exists(_.color != piece.color))
    } yield newPos

    // Includes these new moves with existing ones, passing to the next move validator if applicable.
    callNextMoveValidator(piece, position, field, moves ++ newMoves)
  }
}
