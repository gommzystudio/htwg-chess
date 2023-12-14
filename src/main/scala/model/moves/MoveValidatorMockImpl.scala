package model.moves

import model.pieces.Piece
import model.position.PositionInterface
import model.field.FieldInterface
import model.moves.MoveValidator

case class MoveValidatorMockImpl() extends MoveValidator {
  override def getValidMoves(
      piece: Piece,
      position: PositionInterface,
      field: FieldInterface,
      moves: List[PositionInterface]
  ): List[PositionInterface] = {
    return moves
  }
}
