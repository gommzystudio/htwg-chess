package model.moves

import model.pieces.Piece
import model.Position
import model.Field
import model.GameState

trait MoveValidator {
  var nextMoveValidator: MoveValidator = _

  def setNext(moveValidator: MoveValidator): Unit = {
    nextMoveValidator = moveValidator
  }

  def callNextMoveValidator(
      piece: Piece,
      position: Position,
      field: Field,
      moves: List[Position]
  ): List[Position] = {
    if (nextMoveValidator != null)
      return nextMoveValidator.getValidMoves(piece, position, field, moves)
    else
      return moves
  }

  def getValidMoves(
      piece: Piece,
      position: Position,
      field: Field,
      moves: List[Position]
  ): List[Position]
}
