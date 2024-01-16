package model.moves.special

import model.field.FieldInterface
import model.pieces.{Piece, Pawn, Queen}
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import util.color.Color

class PawnFirstMoveValidatorTest
    extends AnyWordSpec
    with Matchers
    with MockitoSugar {

  "PawnFirstMoveValidator" should {
    "allow a pawn to move two squares forward on its first move for white" in {
      val pawn = mock[Pawn]
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val validator = new PawnFirstMoveValidator()

      when(pawn.color).thenReturn(Color.White)
      when(position.getX()).thenReturn(4)
      when(position.getY()).thenReturn(2)
      when(field.getPiece(any[PositionInterface])).thenReturn(None)
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves should not be empty
      moves should contain(PositionBaseImpl(4, 4))
    }

    "allow a pawn to move two squares forward on its first move for black" in {
      val pawn = mock[Pawn]
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val validator = new PawnFirstMoveValidator()

      when(pawn.color).thenReturn(Color.Black)
      when(position.getX()).thenReturn(4)
      when(position.getY()).thenReturn(7)
      when(field.getPiece(any[PositionInterface])).thenReturn(None)
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves should not be empty
      moves should contain(PositionBaseImpl(4, 5))
    }

    "not allow a pawn to move two squares forward if not on starting position" in {
      val pawn = mock[Pawn]
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val validator = new PawnFirstMoveValidator()

      when(pawn.color).thenReturn(Color.White)
      when(position.getX()).thenReturn(4)
      when(position.getY()).thenReturn(3)
      when(field.getPiece(any[PositionInterface])).thenReturn(None)
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves shouldBe empty
    }

    "not allow a pawn to move two squares forward if the path is blocked" in {
      val pawn = mock[Pawn]
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val blockingPiece = mock[Piece]
      val validator = new PawnFirstMoveValidator()

      when(pawn.color).thenReturn(Color.White)
      when(position.getX()).thenReturn(4)
      when(position.getY()).thenReturn(2)
      when(field.getPiece(any[PositionInterface]))
        .thenReturn(Some(blockingPiece))
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves shouldBe empty
    }

    "throw AssertionError if a non-Pawn piece is passed" in {
      val nonPawnPiece =
        new Queen(Color.White) // Queen is not a Pawn
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val validator = new PawnFirstMoveValidator

      an[AssertionError] should be thrownBy {
        validator.getValidMoves(nonPawnPiece, position, field, List())
      }
    }
  }
}
