package model.moves.special

import model.field.FieldInterface
import model.pieces.{Piece, Pawn, Queen}
import model.position.PositionInterface
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.ArgumentMatchers._
import util.color.Color

class PawnCaptureMoveValidatorTest
    extends AnyWordSpec
    with Matchers
    with MockitoSugar {

  "PawnCaptureMoveValidator" should {
    "validate pawn capture moves correctly for White" in {
      val pawn = mock[Pawn]
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val enemyPiece = mock[Piece]
      val validator = new PawnCaptureMoveValidator()

      when(pawn.color).thenReturn(Color.White)
      when(position.getX()).thenReturn(4)
      when(position.getY()).thenReturn(4)
      when(field.getPiece(any[PositionInterface])).thenReturn(Some(enemyPiece))
      when(enemyPiece.color).thenReturn(Color.Black)
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves should not be empty
      moves.foreach { move =>
        move.getX() should (be >= 3 and be <= 5)
        move.getY() should (be >= 3 and be <= 5)
      }
    }

    "validate pawn capture moves correctly for Black" in {
      val pawn = mock[Pawn]
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val enemyPiece = mock[Piece]
      val validator = new PawnCaptureMoveValidator()

      when(pawn.color).thenReturn(Color.Black)
      when(position.getX()).thenReturn(4)
      when(position.getY()).thenReturn(4)
      when(field.getPiece(any[PositionInterface])).thenReturn(Some(enemyPiece))
      when(enemyPiece.color).thenReturn(Color.White)
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves should not be empty
      moves.foreach { move =>
        move.getX() should (be >= 3 and be <= 5)
        move.getY() should (be >= 3 and be <= 5)
      }
    }

    "not return invalid moves" in {
      val pawn = mock[Pawn]
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val validator = new PawnCaptureMoveValidator()

      when(pawn.color).thenReturn(Color.White)
      when(position.getX()).thenReturn(4)
      when(position.getY()).thenReturn(4)
      when(field.getPiece(any[PositionInterface])).thenReturn(None)
      when(field.isPositionValid(any[PositionInterface])).thenReturn(false)

      val moves = validator.getValidMoves(pawn, position, field, List.empty)

      moves shouldBe empty
    }

    "throw AssertionError if a non-Pawn piece is passed" in {
      val nonPawnPiece =
        new Queen(Color.White) // Queen is not a Pawn
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val validator = new PawnCaptureMoveValidator

      an[AssertionError] should be thrownBy {
        validator.getValidMoves(nonPawnPiece, position, field, List())
      }
    }
  }
}
