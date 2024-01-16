package model.moves

import model.pieces.Piece
import model.position.{PositionBaseImpl, PositionInterface}
import model.field.FieldInterface

final case class StraightMoveValidator(dist: Int = 8) extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    val directions = List((1, 0), (-1, 0), (0, 1), (0, -1))

    val newMoves = directions.flatMap { case (dx, dy) =>
      generateMovesInDirection(piece, position, field, dx, dy, 1)
    }

    return callNextMoveValidator(piece, position, field, moves ++ newMoves)
  }

  private def generateMovesInDirection(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      dx: Int,
      dy: Int,
      step: Int
  ): List[PositionInterface] = {
    if (step > dist) List.empty
    else {
      val newPos =
        PositionBaseImpl(
          position.getX() + step * dx,
          position.getY() + step * dy
        )

      if (!field.isPositionValid(newPos)) List.empty
      else
        field.getPiece(newPos) match {
          case None =>
            newPos :: generateMovesInDirection(
              piece,
              position,
              field,
              dx,
              dy,
              step + 1
            )
          case Some(p) if p.color != piece.color => List(newPos)
          case _                                 => List.empty
        }
    }
  }
}
