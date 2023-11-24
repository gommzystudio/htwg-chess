package test.model.moves.special.sepcial

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import model.Field
import model.Position
import model.pieces.Piece
import model.pieces.Color
import model.pieces.Pawn
import model.moves.special.PawnStandardMoveValidator
import model.moves.MoveValidator

class PawnStandardMoveValidatorSpec extends AnyFlatSpec with Matchers {
  "A PawnStandardMoveValidator" should "add a new position if it is empty" in {
    val piece = Pawn(Color.White)
    val position = Position(1, 1)
    val field = Field(Map())
    val moveValidator = new PawnStandardMoveValidator()

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result should contain theSameElementsAs List(Position(1, 2))
  }

  it should "not add a new position if it is occupied" in {
    val piece = Pawn(Color.White)
    val position = Position(1, 1)
    val field = Field(Map(Position(1, 2) -> Pawn(Color.Black)))
    val moveValidator = new PawnStandardMoveValidator()

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result shouldBe empty
  }

  it should "call the next validator if it exists and the new position is occupied" in {
    val piece = Pawn(Color.White)
    val position = Position(1, 1)
    val field = Field(Map(Position(1, 2) -> Pawn(Color.Black)))
    val nextValidator = new MoveValidator {
      override def getValidMoves(
          piece: Piece,
          position: Position,
          field: Field,
          moves: List[Position]
      ): List[Position] = List(Position(1, 3))
    }
    val moveValidator = new PawnStandardMoveValidator()
    moveValidator.setNext(nextValidator)

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result should contain theSameElementsAs List(Position(1, 3))
  }
}
