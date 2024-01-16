package model.pieces

import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers.any

class RookTest extends AnyWordSpec with Matchers with MockitoSugar {
  "A Rook" when {
    "White" should {
      val rook = new Rook(Color.White)
      "return the correct symbol" in {
        rook.getSymbol() should be("♖")
      }
    }

    "Black" should {
      val rook = new Rook(Color.Black)
      "return the correct symbol" in {
        rook.getSymbol() should be("♜")
      }
    }

    "on an empty board" should {
      val rook = new Rook(Color.White)
      val position = PositionBaseImpl(4, 4)
      val field = mock[FieldInterface]

      "generate all straight moves" in {
        when(field.isPositionValid(any[PositionInterface])).thenReturn(true)
        when(field.getPiece(any[PositionInterface])).thenReturn(None)

        val moves = rook.availableMoves(position, field)

        moves should contain allOf (
          PositionBaseImpl(4, 5),
          PositionBaseImpl(4, 3),
          PositionBaseImpl(5, 4),
          PositionBaseImpl(3, 4)
        )
      }
    }
  }
}
