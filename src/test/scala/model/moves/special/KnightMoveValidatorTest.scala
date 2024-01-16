package model.moves.special

import model.field.FieldInterface
import model.pieces.{Knight, Piece, Queen}
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.ArgumentMatchers._
import util.color.Color

class KnightMoveValidatorTest
    extends AnyWordSpec
    with Matchers
    with MockitoSugar {

  "KnightMoveValidator" should {
    "generate valid moves for a Knight" in {
      val knight = new Knight(Color.White)
      val position = PositionBaseImpl(3, 3)
      val field = mock[FieldInterface]
      val validator = new KnightMoveValidator

      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)
      when(field.getPiece(any[PositionInterface])).thenReturn(None)

      val expectedMoves = Set(
        PositionBaseImpl(2, 5),
        PositionBaseImpl(4, 5),
        PositionBaseImpl(1, 4),
        PositionBaseImpl(1, 2),
        PositionBaseImpl(2, 1),
        PositionBaseImpl(4, 1),
        PositionBaseImpl(5, 4),
        PositionBaseImpl(5, 2)
      )

      val validMoves =
        validator.getValidMoves(knight, position, field, List()).toSet

      validMoves shouldBe expectedMoves
    }

    "exclude invalid positions and positions with same color pieces" in {
      val knight = new Knight(Color.White)
      val position = PositionBaseImpl(0, 0)
      val field = mock[FieldInterface]
      val otherPiece = mock[Piece]
      val validator = new KnightMoveValidator

      when(field.isPositionValid(any[PositionInterface])).thenReturn(false)
      when(field.isPositionValid(PositionBaseImpl(1, 2))).thenReturn(true)
      when(field.isPositionValid(PositionBaseImpl(2, 1))).thenReturn(true)

      when(field.getPiece(PositionBaseImpl(1, 2))).thenReturn(Some(otherPiece))
      when(field.getPiece(PositionBaseImpl(2, 1))).thenReturn(None)
      when(otherPiece.color).thenReturn(Color.White)

      val validMoves = validator.getValidMoves(knight, position, field, List())

      validMoves should contain only PositionBaseImpl(2, 1)
    }

    "throw AssertionError if a non-Knight piece is passed" in {
      val nonKnightPiece =
        new Queen(Color.White) // Queen is not a Knight
      val position = mock[PositionInterface]
      val field = mock[FieldInterface]
      val validator = new KnightMoveValidator

      an[AssertionError] should be thrownBy {
        validator.getValidMoves(nonKnightPiece, position, field, List())
      }
    }
  }
}
