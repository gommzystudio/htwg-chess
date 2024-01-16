package model.pieces

import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers.any

class BishopTest extends AnyWordSpec with Matchers with MockitoSugar {
  "A Bishop" when {
    "White" should {
      val bishop = new Bishop(Color.White)
      "return the correct symbol" in {
        bishop.getSymbol() should be("♗")
      }
    }

    "Black" should {
      val bishop = new Bishop(Color.Black)
      "return the correct symbol" in {
        bishop.getSymbol() should be("♝")
      }
    }

    "on an empty board" should {
      val bishop = new Bishop(Color.White)
      val position = PositionBaseImpl(4, 4)
      val field = mock[FieldInterface]

      "generate all diagonal moves" in {
        when(field.isPositionValid(any[PositionInterface])).thenReturn(true)
        when(field.getPiece(any[PositionInterface])).thenReturn(None)

        val moves = bishop.availableMoves(position, field)

        moves should contain allOf (
          PositionBaseImpl(5, 5),
          PositionBaseImpl(3, 3),
          PositionBaseImpl(5, 3),
          PositionBaseImpl(3, 5)
          // Additional positions for the full range of diagonal moves
        )
      }
    }
  }
}
