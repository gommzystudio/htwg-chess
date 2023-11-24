package test.model.moves.special.sepcial

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import model.Field
import model.Position
import model.pieces.Piece
import model.pieces.Color
import model.pieces.Pawn
import model.moves.special.PawnCaptureMoveValidator
import model.moves.MoveValidator

class PawnCaptureMoveValidatorSpec extends AnyFlatSpec with Matchers {
  "A PawnCaptureMoveValidator" should "add left and right positions if they are occupied by opposite color pieces" in {
    val piece = Pawn(Color.White)
    val position = Position(2, 2)
    val field = Field(
      Map(
        Position(1, 3) -> Pawn(Color.Black),
        Position(3, 3) -> Pawn(Color.Black)
      )
    )
    val moveValidator = new PawnCaptureMoveValidator()

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result should contain theSameElementsAs List(Position(1, 3), Position(3, 3))
  }

  it should "not add left and right positions if they are occupied by same color pieces" in {
    val piece = Pawn(Color.White)
    val position = Position(2, 2)
    val field = Field(
      Map(
        Position(1, 3) -> Pawn(Color.White),
        Position(3, 3) -> Pawn(Color.White)
      )
    )
    val moveValidator = new PawnCaptureMoveValidator()

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result shouldBe empty
  }

  it should "not add left and right positions if they are empty" in {
    val piece = Pawn(Color.White)
    val position = Position(2, 2)
    val field = Field(Map())
    val moveValidator = new PawnCaptureMoveValidator()

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result shouldBe empty
  }

  it should "call the next validator if it exists and the new positions are occupied by same color pieces" in {
    val piece = Pawn(Color.White)
    val position = Position(2, 2)
    val field = Field(
      Map(
        Position(1, 3) -> Pawn(Color.White),
        Position(3, 3) -> Pawn(Color.White)
      )
    )
    val nextValidator = new MoveValidator {
      override def getValidMoves(
          piece: Piece,
          position: Position,
          field: Field,
          moves: List[Position]
      ): List[Position] = List(Position(2, 3))
    }
    val moveValidator = new PawnCaptureMoveValidator()
    moveValidator.setNext(nextValidator)

    val result = moveValidator.getValidMoves(piece, position, field, List())

    result should contain theSameElementsAs List(Position(2, 3))
  }
}
