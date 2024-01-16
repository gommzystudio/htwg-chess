package model.moves.special

import model.field.FieldInterface
import model.pieces.{Pawn, Queen}
import model.position.{PositionInterface, PositionBaseImpl}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color

class PawnStandardMoveValidatorTest
    extends AnyWordSpec
    with Matchers
    with MockitoSugar {

  "PawnStandardMoveValidator" should {
    val validator = new PawnStandardMoveValidator()

    "allow a White pawn to move one step forward if the position is valid and empty" in {
      val pawn = new Pawn(Color.White)
      val position = PositionBaseImpl(1, 1)
      val newPosition = PositionBaseImpl(1, 2)
      val field = mock[FieldInterface]

      when(field.getPiece(newPosition)).thenReturn(None)
      when(field.isPositionValid(newPosition)).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves should contain(newPosition)
    }

    "prevent a White pawn from moving forward if the position is not valid or not empty" in {
      val pawn = new Pawn(Color.White)
      val position = PositionBaseImpl(1, 1)
      val newPosition = PositionBaseImpl(1, 2)
      val field = mock[FieldInterface]

      when(field.getPiece(newPosition))
        .thenReturn(Some(mock[Pawn])) // position not empty
      when(field.isPositionValid(newPosition)).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves should not contain newPosition
    }

    "allow a Black pawn to move one step forward if the position is valid and empty" in {
      val pawn = new Pawn(Color.Black)
      val position = PositionBaseImpl(1, 6)
      val newPosition = PositionBaseImpl(1, 5)
      val field = mock[FieldInterface]

      when(field.getPiece(newPosition)).thenReturn(None)
      when(field.isPositionValid(newPosition)).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves should contain(newPosition)
    }

    "prevent a Black pawn from moving forward if the position is not valid or not empty" in {
      val pawn = new Pawn(Color.Black)
      val position = PositionBaseImpl(1, 6)
      val newPosition = PositionBaseImpl(1, 5)
      val field = mock[FieldInterface]

      when(field.getPiece(newPosition))
        .thenReturn(Some(mock[Pawn])) // position not empty
      when(field.isPositionValid(newPosition)).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves should not contain newPosition
    }

    "throw AssertionError if a non-Pawn piece is passed" in {
      val nonPawnPiece =
        new Queen(Color.White) // Queen is not a Pawn
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val validator = new PawnStandardMoveValidator

      an[AssertionError] should be thrownBy {
        validator.getValidMoves(nonPawnPiece, position, field, List())
      }
    }
  }
}
