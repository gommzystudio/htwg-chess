package model.pieces

import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers.any

class KnightTest extends AnyWordSpec with Matchers with MockitoSugar {
  "A Knight" when {
    "White" should {
      val knight = new Knight(Color.White)
      "return the correct symbol" in {
        knight.getSymbol() should be("♘")
      }
    }

    "Black" should {
      val knight = new Knight(Color.Black)
      "return the correct symbol" in {
        knight.getSymbol() should be("♞")
      }
    }

    "on an empty board" should {
      val knight = new Knight(Color.White)
      val position = PositionBaseImpl(4, 4)
      val field = mock[FieldInterface]

      "generate all L-shaped moves" in {
        when(field.isPositionValid(any[PositionInterface])).thenReturn(true)
        when(field.getPiece(any[PositionInterface])).thenReturn(None)

        val moves = knight.availableMoves(position, field)

        moves should contain allOf (
          PositionBaseImpl(6, 5),
          PositionBaseImpl(6, 3),
          PositionBaseImpl(2, 5),
          PositionBaseImpl(2, 3),
          PositionBaseImpl(5, 6),
          PositionBaseImpl(3, 6),
          PositionBaseImpl(5, 2),
          PositionBaseImpl(3, 2)
        )
      }
    }
  }
}
