package model.pieces

import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers.any

class PawnTest extends AnyWordSpec with Matchers with MockitoSugar {
  "Pawn" should {
    "White" should {
      val pawn = new Pawn(Color.White)
      "return the correct symbol" in {
        pawn.getSymbol() should be("♙")
      }
    }

    "Black" should {
      val pawn = new Pawn(Color.Black)
      "return the correct symbol" in {
        pawn.getSymbol() should be("♟")
      }
    }

    "on an empty board" should {
      val pawn = new Pawn(Color.White)
      val position = PositionBaseImpl(4, 2)
      val field = mock[FieldInterface]

      "generate all valid moves" in {
        when(field.isPositionValid(any[PositionInterface])).thenReturn(true)
        when(field.getPiece(any[PositionInterface])).thenReturn(None)

        val moves = pawn.availableMoves(position, field)

        moves should contain allOf (
          PositionBaseImpl(4, 3), // Standard move
          PositionBaseImpl(4, 4) // First move
          // Capture moves are not included because they require an opponent's piece to be present
        )
      }
    }
  }
}
