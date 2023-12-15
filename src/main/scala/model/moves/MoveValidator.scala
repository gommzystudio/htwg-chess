package model.moves

import model.pieces.Piece
import model.position.PositionInterface
import model.field.FieldInterface
import model.commands.MoveCommand
import scala.util.Success
import scala.util.Failure

trait MoveValidator {
  var nextMoveValidator: MoveValidator = _

  def setNext(moveValidator: MoveValidator): Unit = {
    nextMoveValidator = moveValidator
  }

  def callNextMoveValidator(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    if (nextMoveValidator != null)
      return nextMoveValidator.getValidMoves(piece, position, field, moves)
    else
      return moves
  }

  def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface]
}
