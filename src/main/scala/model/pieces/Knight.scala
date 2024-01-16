package model.pieces

import model.field.FieldInterface
import model.position.PositionInterface
import model.moves.special.KnightMoveValidator
import util.color.Color

class Knight(c: Color) extends Piece(c) {
  override def getSymbol() = if (c == Color.White) "♘" else "♞"

  override def availableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    val knightMoveValidator = new KnightMoveValidator()

    return knightMoveValidator.getValidMoves(
      this,
      position,
      field,
      List()
    )
  }
}
