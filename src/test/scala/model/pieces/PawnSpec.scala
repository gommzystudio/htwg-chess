package test.model.pieces

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.{Field, Position}
import model.pieces.{Color, Pawn}
import model.FieldFactory

class PawnSpec extends AnyWordSpec with Matchers {
  "A Pawn" when {
    "availableMoves is called" should {
      "return a list for a white piece" in {
        val pawn = Pawn(Color.White)
        val field = FieldFactory.createInitialField()
        val position = Position(1, 2)
        pawn.availableMoves(position, field).isInstanceOf[List[Position]]
      }
      "return a list for a black piece" in {
        val pawn = Pawn(Color.Black)
        val field = FieldFactory.createInitialField()
        val position = Position(1, 7)
        pawn.availableMoves(position, field).isInstanceOf[List[Position]]
      }
    }
  }
}
