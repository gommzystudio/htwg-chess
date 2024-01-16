package model.moves

import model.pieces.Piece
import model.position.PositionInterface
import model.field.FieldInterface

trait MoveValidator {
  var nextMoveValidator: Option[MoveValidator] = None

  def next(moveValidator: MoveValidator): Unit = {
    nextMoveValidator = Some(moveValidator)
  }

  def callNextMoveValidator(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    nextMoveValidator match {
      case Some(validator) =>
        validator.getValidMoves(piece, position, field, moves)
      case None => moves
    }
  }

  def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface]
}
