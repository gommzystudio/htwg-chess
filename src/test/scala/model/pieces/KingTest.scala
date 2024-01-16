package model.pieces

import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers.any

class KingTest extends AnyWordSpec with Matchers with MockitoSugar {
  "A King" when {
    "White" should {
      val king = new King(Color.White)
      "return the correct symbol" in {
        king.getSymbol() should be("♔")
      }
    }

    "Black" should {
      val king = new King(Color.Black)
      "return the correct symbol" in {
        king.getSymbol() should be("♚")
      }
    }

    "on an empty board" should {
      val king = new King(Color.White)
      val position = PositionBaseImpl(4, 4)
      val field = mock[FieldInterface]

      "generate all straight and diagonal moves" in {
        when(field.isPositionValid(any[PositionInterface])).thenReturn(true)
        when(field.getPiece(any[PositionInterface])).thenReturn(None)

        val moves = king.availableMoves(position, field)

        moves should contain allOf (
          PositionBaseImpl(4, 5),
          PositionBaseImpl(4, 3),
          PositionBaseImpl(5, 4),
          PositionBaseImpl(3, 4),
          PositionBaseImpl(5, 5),
          PositionBaseImpl(3, 3),
          PositionBaseImpl(5, 3),
          PositionBaseImpl(3, 5)
        )
      }
    }
  }
}
