package model.pieces

import scala.annotation.switch
import scala.collection.mutable.ArrayBuffer

import model.position.PositionInterface
import model.field.FieldInterface
import model.moves.special.PawnFirstMoveValidator
import model.moves.special.PawnStandardMoveValidator
import model.moves.special.PawnCaptureMoveValidator
import scala.compiletime.ops.boolean
import model.field.FieldInterface
import model.position.PositionInterface
import model.pieces.Piece
import util.color.Color

class Pawn(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♙" else "♟"
  }

  override def whiteAvailableMoves(
      position: PositionInterface,
      field: FieldInterface
  ): List[PositionInterface] = {
    val standardMoveValidator = new PawnStandardMoveValidator()
    val firstMoveValidator = new PawnFirstMoveValidator()
    val captureMoveValidator = new PawnCaptureMoveValidator()

    standardMoveValidator.setNext(firstMoveValidator)
    firstMoveValidator.setNext(captureMoveValidator)

    return standardMoveValidator.getValidMoves(
      this,
      position,
      field,
      List()
    )
  }
}
