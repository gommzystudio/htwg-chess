package test.model

import org.scalatest.wordspec.AnyWordSpec
import model.Field
import model.pieces.Rook
import model.pieces.Color
import model.Position

class FieldSpec extends AnyWordSpec {
  "A Field" when {
    "created" should {
      "return an map with 32 pieces when pieces is called" in {
        val field = new Field()
        assert(field.pieces.size === 32)
      }
    }
    "gives a piece" should {
      "return the piece" in {
        val field = new Field()
        val piece = field.getPiece(1, 1)
        assert(piece.get === field.pieces.get(new Position(1, 1)).get)
      }
    }
    "can check move leglity" should {
      "return true when the move is legal" in {
        val field = new Field()
        val piece = field.getPiece(1, 2).get
        val from = new Position(1, 2)
        val to = new Position(1, 3)
        assert(field.checkLegality(piece, from, to) === true)
      }
      "return false when the move is illegal" in {
        val field = new Field()
        val piece = field.getPiece(1, 2).get
        val from = new Position(1, 2)
        val to = new Position(1, 1)
        assert(field.checkLegality(piece, from, to) === false)
      }
    }
    "when movePiece is called" should {
      "return a new Field with the piece moved" in {
        val field = new Field()
        val from = new Position(1, 2)
        val to = new Position(1, 3)
        val newField = field.movePiece(from, to)
        assert(newField.pieces.get(to).get === field.pieces.get(from).get)
      }
      "do nothing when there is no piece at the from position" in {
        val field = new Field()
        val from = new Position(1, 3)
        val to = new Position(1, 4)
        val newField = field.movePiece(from, to)
        assert(newField.pieces.get(to) === None)
      }
    }
    "a field can be created with a custom map" should {
      "return a new Field with the custom map" in {
        val map = Map(new Position(1, 1) -> new Rook(Color.White))
        val field = new Field(map)
        assert(
          field.pieces.get(new Position(1, 1)).get === map
            .get(new Position(1, 1))
            .get
        )
      }
    }
    "flipBoard is called" should {
      "return a new Field with the board flipped" in {
        val field = new Field({
          Map(new Position(1, 1) -> new Rook(Color.White))
        })
        val newField = field.flipBoard()
        assert(
          newField.pieces.get(new Position(1, 1)).get === field.pieces
            .get(new Position(1, 8))
            .get
        )
      }
    }
  }
}
