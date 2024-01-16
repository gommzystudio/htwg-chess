package model.moves.special

import model.moves.MoveValidator
import model.pieces.{Piece, Knight}
import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}

class KnightMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Knight])

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

    val newMoves = for {
      (dx, dy) <- directions
      newPos = PositionBaseImpl(position.getX() + dx, position.getY() + dy)
      if field.isPositionValid(newPos) && (field
        .getPiece(newPos)
        .isEmpty || field.getPiece(newPos).exists(_.color != piece.color))
    } yield newPos

    return callNextMoveValidator(piece, position, field, moves ++ newMoves)
  }
}
