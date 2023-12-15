package model.moves.special

import model.moves.MoveValidator
import model.pieces.Piece
import model.field.FieldInterface
import model.position.PositionBaseImpl
import model.position.PositionInterface
import scala.collection.mutable.ArrayBuffer
import model.pieces.Knight

class KnightMoveValidator extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    assert(piece.isInstanceOf[Knight])

    var newMoves = moves

    val upLeftPosition =
      PositionBaseImpl(position.getX() - 1, position.getY() + 2)
    val upRightPosition =
      PositionBaseImpl(position.getX() + 1, position.getY() + 2)
    val leftUpPosition =
      PositionBaseImpl(position.getX() - 2, position.getY() + 1)
    val leftDownPosition =
      PositionBaseImpl(position.getX() - 2, position.getY() - 1)
    val downLeftPosition =
      PositionBaseImpl(position.getX() - 1, position.getY() - 2)
    val downRightPosition =
      PositionBaseImpl(position.getX() + 1, position.getY() - 2)
    val rightUpPosition =
      PositionBaseImpl(position.getX() + 2, position.getY() + 1)
    val rightDownPosition =
      PositionBaseImpl(position.getX() + 2, position.getY() - 1)

    val positions = List(
      upLeftPosition,
      upRightPosition,
      leftUpPosition,
      leftDownPosition,
      downLeftPosition,
      downRightPosition,
      rightUpPosition,
      rightDownPosition
    )

    for (pos <- positions) {
      if (
        field.getPiece(pos.x, pos.y) != None && field
          .getPiece(pos.x, pos.y)
          .get
          .color != piece.color
        || field.getPiece(pos.x, pos.y) == None
      ) {
        newMoves = pos :: newMoves
      }
    }

    return callNextMoveValidator(piece, position, field, newMoves)
  }
}
