package model.pieces

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import util.color.Color

class PieceFactoryTest extends AnyWordSpec with Matchers {
  "A PieceFactory" when {
    "creating a piece" should {
      "create a Bishop" in {
        val piece = PieceFactory.createPiece("Bishop", Color.White)
        piece shouldBe a[Bishop]
        piece.color shouldBe Color.White
      }

      "create a King" in {
        val piece = PieceFactory.createPiece("King", Color.White)
        piece shouldBe a[King]
        piece.color shouldBe Color.White
      }

      "create a Knight" in {
        val piece = PieceFactory.createPiece("Knight", Color.White)
        piece shouldBe a[Knight]
        piece.color shouldBe Color.White
      }

      "create a Pawn" in {
        val piece = PieceFactory.createPiece("Pawn", Color.White)
        piece shouldBe a[Pawn]
        piece.color shouldBe Color.White
      }

      "create a Queen" in {
        val piece = PieceFactory.createPiece("Queen", Color.White)
        piece shouldBe a[Queen]
        piece.color shouldBe Color.White
      }

      "create a Rook" in {
        val piece = PieceFactory.createPiece("Rook", Color.White)
        piece shouldBe a[Rook]
        piece.color shouldBe Color.White
      }
    }
  }
}
