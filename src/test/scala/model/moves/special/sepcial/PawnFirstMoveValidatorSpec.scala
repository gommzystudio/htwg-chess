package test.model.moves.special.sepcial

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import model.Field
import model.Position
import model.pieces.Piece
import model.pieces.Color
import model.pieces.Pawn
import model.moves.special.PawnFirstMoveValidator
import model.moves.MoveValidator

class PawnFirstMoveValidatorSpec extends AnyFlatSpec with Matchers {
  "getValidMoves" should "add a new position if it is empty and the pawn is in its initial position" in {
    val piece = Pawn(Color.White)
    val position = Position(1, 2)
    val field = Field(Map())
    val moveValidator = new PawnFirstMoveValidator()

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result should contain theSameElementsAs List(Position(1, 4))
  }

  it should "not add a new position if it is occupied" in {
    val piece = Pawn(Color.White)
    val position = Position(1, 2)
    val field = Field(Map(Position(1, 4) -> Pawn(Color.Black)))
    val moveValidator = new PawnFirstMoveValidator()

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result shouldBe empty
  }

  it should "not add a new position if the skip position is occupied" in {
    val piece = Pawn(Color.White)
    val position = Position(1, 2)
    val field = Field(Map(Position(1, 3) -> Pawn(Color.Black)))
    val moveValidator = new PawnFirstMoveValidator()

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result shouldBe empty
  }

  it should "not add a new position if the pawn is not in its initial position" in {
    val piece = Pawn(Color.White)
    val position = Position(1, 3)
    val field = Field(Map())
    val moveValidator = new PawnFirstMoveValidator()

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result shouldBe empty
  }
}
