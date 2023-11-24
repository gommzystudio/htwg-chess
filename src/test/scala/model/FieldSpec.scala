package test.model

import org.scalatest.wordspec.AnyWordSpec
import model.Field
import model.pieces.Rook
import model.pieces.Color
import model.Position
import model.FieldFactory

class FieldSpec extends AnyWordSpec {
  "A Field" when {
    "gives a piece" should {
      "return the piece" in {
        val field = FieldFactory.createInitialField()
        val piece = field.getPiece(1, 1)
        assert(piece.get === field.pieces.get(new Position(1, 1)).get)
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
          newField.pieces.get(new Position(8, 8)).get === field.pieces
            .get(new Position(1, 1))
            .get
        )
      }
    }
  }
}
