package model.moves

import model.moves.MoveValidator
import model.pieces.Piece
import model.position.PositionInterface
import model.position.PositionBaseImpl
import model.field.FieldInterface
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

final case class DiagonalMoveValidator(dist: Int = 8) extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    var newMoves = moves

    breakable {
      for (i <- position.getX() + 1 to math.min(8, position.getX() + dist)) {
        val newPosition =
          PositionBaseImpl(i, position.getY() + i - position.getX())
        if (field.getPiece(newPosition).isEmpty) {
          newMoves = newMoves :+ newPosition
        } else {
          if (field.getPiece(newPosition).get.color != piece.color) {
            newMoves = newMoves :+ newPosition
          }
          break()
        }
      }
    }

    breakable {
      for (
        i <- position.getX() - 1 to math.max(1, position.getX() - dist) by -1
      ) {
        val newPosition =
          PositionBaseImpl(i, position.getY() - i + position.getX())
        if (field.getPiece(newPosition).isEmpty) {
          newMoves = newMoves :+ newPosition
        } else {
          if (field.getPiece(newPosition).get.color != piece.color) {
            newMoves = newMoves :+ newPosition
          }
          break()
        }
      }
    }

    breakable {
      for (i <- position.getX() + 1 to math.min(8, position.getX() + dist)) {
        val newPosition =
          PositionBaseImpl(i, position.getY() - i + position.getX())
        if (field.getPiece(newPosition).isEmpty) {
          newMoves = newMoves :+ newPosition
        } else {
          if (field.getPiece(newPosition).get.color != piece.color) {
            newMoves = newMoves :+ newPosition
          }
          break()
        }
      }
    }

    breakable {
      for (
        i <- position.getX() - 1 to math.max(1, position.getX() - dist) by -1
      ) {
        val newPosition =
          PositionBaseImpl(i, position.getY() + i - position.getX())
        if (field.getPiece(newPosition).isEmpty) {
          newMoves = newMoves :+ newPosition
        } else {
          if (field.getPiece(newPosition).get.color != piece.color) {
            newMoves = newMoves :+ newPosition
          }
          break()
        }
      }
    }

    return callNextMoveValidator(piece, position, field, newMoves)
  }
}
