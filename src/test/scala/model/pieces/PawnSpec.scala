package test.model.pieces

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.{Field, Position}
import model.pieces.{Color, Pawn}

class PawnSpec extends AnyWordSpec with Matchers {
  "A Pawn" when {
    "availableMoves is called" should {
      "return a single move forward if the path is clear" in {
        val pawn = Pawn(Color.White)
        val position = Position(1, 2)
        val field = Field()
        pawn.availableMoves(position, field) should contain(Position(1, 3))
      }
      "return a double move forward if the pawn is in its initial position and the path is clear" in {
        val pawn = Pawn(Color.White)
        val position = Position(1, 2)
        val field = Field()
        pawn.availableMoves(position, field) should contain(Position(1, 4))
      }
      "return capture moves if there are opponent pieces diagonally in front" in {
        val pawn = Pawn(Color.White)
        val position = Position(1, 2)
        val field = Field(Map(Position(2, 3) -> Pawn(Color.Black)))
        pawn.availableMoves(position, field) should contain(Position(2, 3))
      }

      "availableMoves is called and the pawn is in the promotion row" should {
        "return promotion moves" in {
          val pawn = Pawn(Color.White)
          val position = Position(1, 7)
          val field = Field(Map(Position(1, 7) -> Pawn(Color.White)))
          val moves = pawn.availableMoves(position, field)
          moves should contain allOf (Position(1, 8), Position(2, 8))
        }
      }

      "availableMoves is called and the pawn can capture en passant" should {
        "return the en passant capture move" in {
          val pawn = Pawn(Color.White)
          val position = Position(5, 5)
          val field = Field(
            Map(
              Position(5, 5) -> Pawn(Color.White),
              Position(6, 7) -> Pawn(Color.Black)
            )
          )
          field.movePiece(Position(6, 7), Position(6, 5))
          pawn.availableMoves(position, field) should contain(Position(6, 6))
        }
      }
    }
  }
}
