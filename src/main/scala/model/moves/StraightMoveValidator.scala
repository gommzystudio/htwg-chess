package model.moves

import model.pieces.Piece
import model.position.{PositionBaseImpl, PositionInterface}
import model.field.FieldInterface

// StraightMoveValidator determines valid straight moves (vertical and horizontal) for pieces like Rooks.
final case class StraightMoveValidator(dist: Int = 8) extends MoveValidator {

  // Calculates valid straight moves for a piece based on its current position and the state of the field.
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    // Four straight directions: up, down, left, and right.
    val directions = List((1, 0), (-1, 0), (0, 1), (0, -1))

    // Collects new moves in all straight directions.
    val newMoves = directions.flatMap { case (dx, dy) =>
      generateMovesInDirection(piece, position, field, dx, dy, 1)
    }

    callNextMoveValidator(piece, position, field, moves ++ newMoves)
  }

  // Generates moves in a specific straight direction up to a certain distance.
  private def generateMovesInDirection(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      dx: Int, // X direction (0 for vertical, -1 or 1 for horizontal)
      dy: Int, // Y direction (0 for horizontal, -1 or 1 for vertical)
      step: Int // Current step from the original position
  ): List[PositionInterface] = {
    if (step > dist) List.empty // Stop if the maximum distance is reached.
    else {
      val newPos = PositionBaseImpl(
        position.getX() + step * dx,
        position.getY() + step * dy
      )

      if (!field.isPositionValid(newPos))
        List.empty // Stop if the new position is invalid.
      else
        field.getPiece(newPos) match {
          case None => // If the new position is empty, include it and continue.
            newPos :: generateMovesInDirection(
              piece,
              position,
              field,
              dx,
              dy,
              step + 1
            )
          case Some(p) if p.color != piece.color =>
            List(
              newPos
            ) // If there's an opponent piece, include the position and stop.
          case _ =>
            List.empty // If there's a friendly piece or any other obstacle, stop.
        }
    }
  }
}
