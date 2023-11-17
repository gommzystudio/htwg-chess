package test.model.moves.special

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import model.Field
import model.Position
import model.pieces.Piece
import model.pieces.Color
import model.pieces.Pawn
import model.moves.MoveValidator

class MoveValidatorSpec extends AnyFlatSpec with Matchers {
  "A MoveValidator" should "call the next validator if next validator is not null" in {
    val piece = Pawn(Color.White)
    val position = Position(1, 1)
    val field = Field(Map())
    val nextValidator = new MoveValidator {
      override def getValidMoves(
          piece: Piece,
          position: Position,
          field: Field,
          moves: List[Position]
      ): List[Position] = List(Position(1, 2))
    }
    val moveValidator = new MoveValidator {
      override def getValidMoves(
          piece: Piece,
          position: Position,
          field: Field,
          moves: List[Position]
      ): List[Position] = List()
    }
    moveValidator.setNext(nextValidator)

    val result =
      moveValidator.callNextMoveValidator(piece, position, field, List())

    result should contain theSameElementsAs List(Position(1, 2))
  }


  it should "not call the next validator if next validator is null" in {
    val piece = Pawn(Color.White)
    val position = Position(1, 1)
    val field = Field(Map())
    val moveValidator = new MoveValidator {
      override def getValidMoves(
          piece: Piece,
          position: Position,
          field: Field,
          moves: List[Position]
      ): List[Position] = List()
    }

    val result =
      moveValidator.callNextMoveValidator(piece, position, field, List())

    result shouldBe empty
  }
}
