package test.model.pieces

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.{Field, Position}
import model.pieces.Rook
import model.pieces.Color
import model.FieldFactory

class PieceSpec extends AnyWordSpec with Matchers {
  "A Piece" when {
    "when created" should {
      "have a color" in {
        val piece = Rook(Color.White)
        assert(piece.color === Color.White)
      }
    }
    "availableMoves is called" should {
      "throw NotImplementedError" in {
        val piece = Rook(Color.White)
        val position = Position(1, 1)
        val field = FieldFactory.createInitialField()
        piece.availableMoves(position, field)

      }
    }
    "getSymbol is called" should {
      "throw NotImplementedError" in {
        val piece = Rook(Color.White)
        piece.getSymbol()

      }
    }
  }
}
