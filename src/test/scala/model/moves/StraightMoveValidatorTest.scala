package model.moves

import model.field.FieldInterface
import model.pieces.{Piece, Rook}
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers._

class StraightMoveValidatorTest
    extends AnyWordSpec
    with Matchers
    with MockitoSugar {

  "StraightMoveValidator" should {
    val validator = new StraightMoveValidator()
    val piece =
      new Rook(
        Color.White
      ) // Using Rook as an example of a straight-moving piece
    val position = PositionBaseImpl(4, 4)
    val field = mock[FieldInterface]

    "allow moves in all straight directions within the board boundaries" in {
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)
      when(field.getPiece(any[PositionInterface])).thenReturn(None)

      val moves = validator.getValidMoves(piece, position, field, List.empty)

      moves should contain allOf (
        PositionBaseImpl(4, 5),
        PositionBaseImpl(4, 3),
        PositionBaseImpl(5, 4),
        PositionBaseImpl(3, 4)
      )
    }

    "capture an opponent's piece but not move beyond it" in {
      val opponentPiecePosition = PositionBaseImpl(4, 5)
      val opponentPiece = mock[Piece]
      when(opponentPiece.color).thenReturn(Color.Black)
      when(field.getPiece(opponentPiecePosition))
        .thenReturn(Some(opponentPiece))
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(piece, position, field, List.empty)

      moves should contain(opponentPiecePosition)
      moves should not contain PositionBaseImpl(
        4,
        6
      ) // Ensure it does not move beyond the captured piece
    }
    "not move onto a position occupied by a piece of the same color" in {
      val blockedPosition = PositionBaseImpl(4, 5)
      val blockingPiece = mock[Piece]
      when(blockingPiece.color).thenReturn(Color.White)
      when(field.getPiece(blockedPosition)).thenReturn(Some(blockingPiece))
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(piece, position, field, List.empty)

      moves should not contain blockedPosition
    }

    "not move beyond the specified distance" in {
      val distantPosition =
        PositionBaseImpl(4, 12) // Beyond the default distance of 8
      when(field.isPositionValid(distantPosition)).thenReturn(true)
      when(field.getPiece(distantPosition)).thenReturn(None)

      val moves = validator.getValidMoves(piece, position, field, List.empty)

      moves should not contain distantPosition
    }
  }
}
