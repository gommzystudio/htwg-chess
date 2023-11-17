package test.model.pieces

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.Field
import model.Position
import model.pieces.Piece
import model.pieces.Color

class PieceSpec extends AnyWordSpec with Matchers {
  "A Piece" when {
    "when created" should {
      "have a color" in {
        val piece = Piece(Color.White)
        assert(piece.color === Color.White)
      }
    }
    "availableMoves is called" should {
      "throw NotImplementedError" in {
        val piece = Piece(Color.White)
        val position = Position(1, 1)
        val field = Field()
        assertThrows[NotImplementedError] {
          piece.availableMoves(position, field)
        }
      }
    }
    "getSymbol is called" should {
      "throw NotImplementedError" in {
        val piece = Piece(Color.White)
        assertThrows[NotImplementedError] {
          piece.getSymbol()
        }
      }
    }
  }
}
