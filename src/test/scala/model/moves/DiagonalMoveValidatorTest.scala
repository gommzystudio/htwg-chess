package model.moves

import model.field.FieldInterface
import model.pieces.{Piece, Bishop}
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers._
import model.moves.DiagonalMoveValidator

class DiagonalMoveValidatorTest
    extends AnyWordSpec
    with Matchers
    with MockitoSugar {

  "DiagonalMoveValidator" should {
    val validator = new DiagonalMoveValidator()
    val piece =
      new Bishop(
        Color.White
      ) // Using Bishop as an example of a diagonal-moving piece
    val position = PositionBaseImpl(4, 4)
    val field = mock[FieldInterface]

    "allow moves in all diagonal directions within the board boundaries" in {
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)
      when(field.getPiece(any[PositionInterface])).thenReturn(None)

      val moves = validator.getValidMoves(piece, position, field, List.empty)

      moves should contain allOf (
        PositionBaseImpl(5, 5),
        PositionBaseImpl(5, 3),
        PositionBaseImpl(3, 5),
        PositionBaseImpl(3, 3)
      )
    }

    "capture an opponent's piece but not move beyond it" in {
      val opponentPiecePosition = PositionBaseImpl(5, 5)
      val opponentPiece = mock[Piece]
      when(opponentPiece.color).thenReturn(Color.Black)
      when(field.getPiece(opponentPiecePosition))
        .thenReturn(Some(opponentPiece))
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(piece, position, field, List.empty)

      moves should contain(opponentPiecePosition)
      moves should not contain PositionBaseImpl(6, 6)
    }

    "not move onto a position occupied by a piece of the same color" in {
      val blockedPosition = PositionBaseImpl(5, 5)
      val blockingPiece = mock[Piece]
      when(blockingPiece.color).thenReturn(Color.White)
      when(field.getPiece(blockedPosition)).thenReturn(Some(blockingPiece))
      when(field.isPositionValid(any[PositionInterface])).thenReturn(true)

      val moves = validator.getValidMoves(piece, position, field, List.empty)

      moves should not contain blockedPosition
    }
  }
}
