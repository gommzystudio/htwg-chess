package model.field

import model.pieces.{Piece, King, Queen}
import model.position.{PositionInterface, PositionBaseImpl}
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.ArgumentMatchers._
import util.color.Color

class FieldBaseImplTest extends AnyWordSpec with Matchers with MockitoSugar {

  "A FieldBaseImpl" when {
    "getPieces" should {
      "return the correct pieces" in {
        val piecesMap = Map[PositionInterface, Piece](
          PositionBaseImpl(1, 1) -> mock[Piece],
          PositionBaseImpl(2, 2) -> mock[Piece]
        )
        val field = FieldBaseImpl(piecesMap)

        field.getPieces() shouldEqual piecesMap
      }
    }

    "getting a piece at a position" should {
      "return the correct piece" in {
        val piecesMap = Map[PositionInterface, Piece](
          PositionBaseImpl(1, 1) -> mock[Piece],
          PositionBaseImpl(2, 2) -> mock[Piece]
        )
        val field = FieldBaseImpl(piecesMap)
        val position = PositionBaseImpl(1, 1)

        field.getPiece(position) shouldEqual piecesMap.get(position)
      }
    }

    "checking for checkmate" should {
      "return None if not checkmate" in {
        val mockPiece = mock[Piece]
        when(mockPiece.color).thenReturn(Color.White)
        when(
          mockPiece.availableMoves(any[PositionInterface], any[FieldInterface])
        )
          .thenReturn(List(PositionBaseImpl(3, 3)))

        val field = FieldBaseImpl(Map(PositionBaseImpl(1, 1) -> mockPiece))

        field.isCheckMate() shouldBe None
      }

      "return the color of the player if checkmate" in {
        val king = new King(Color.White)
        val attackingPiece = new Queen(Color.Black)
        val protectingAttackingPiece = new Queen(Color.Black)

        val field = FieldBaseImpl(
          Map(
            PositionBaseImpl(1, 1) -> king,
            PositionBaseImpl(2, 2) -> attackingPiece,
            PositionBaseImpl(3, 3) -> protectingAttackingPiece
          )
        )

        field.isCheckMate() shouldBe Some(Color.White)
      }
    }

    "setting a piece on the board" should {
      "place the piece correctly" in {
        val field = FieldBaseImpl(Map.empty)
        val position = PositionBaseImpl(1, 1)
        val piece = mock[Piece]

        val updatedField = field.setPiece(position, piece)
        updatedField.getPiece(position) shouldBe Some(piece)
      }
    }

    "removing a piece from the board" should {
      "remove the piece correctly" in {
        val position = PositionBaseImpl(1, 1)
        val piece = mock[Piece]
        val field = FieldBaseImpl(Map(position -> piece))

        val updatedField = field.removePiece(position)
        updatedField.getPiece(position) shouldBe None
      }
    }

    "flipping the board" should {
      "flip all pieces' positions" in {
        val position1 = PositionBaseImpl(1, 1)
        val position2 = PositionBaseImpl(2, 2)
        val piece1 = mock[Piece]
        val piece2 = mock[Piece]
        val field = FieldBaseImpl(Map(position1 -> piece1, position2 -> piece2))

        val flippedField = field.flipBoard()
        flippedField.getPiece(position1.flipPosition()) shouldBe Some(piece1)
        flippedField.getPiece(position2.flipPosition()) shouldBe Some(piece2)
      }
    }

    "getting a piece using x and y coordinates" should {
      "return the correct piece" in {
        val piece = mock[Piece]
        val field = FieldBaseImpl(Map(PositionBaseImpl(3, 4) -> piece))

        field.getPiece(3, 4) shouldBe Some(piece)
      }
    }

    "flipping the player" should {
      "switch the current player" in {
        val field = FieldBaseImpl(Map.empty, Color.White)

        val flippedField = field.flipPlayer()
        flippedField.getCurrentPlayer() shouldBe Color.Black
      }
    }

    "checking if a position is under attack (isCheck)" should {
      "return true if the king of a specific color is in check" in {
        val kingPosition = PositionBaseImpl(1, 1)
        val attackingPiecePosition = PositionBaseImpl(2, 2)
        val king = new King(Color.White)
        val attackingPiece = mock[Piece]
        when(attackingPiece.color).thenReturn(Color.Black)
        when(
          attackingPiece.availableMoves(
            any[PositionInterface],
            any[FieldInterface]
          )
        )
          .thenReturn(List(kingPosition))

        val field = FieldBaseImpl(
          Map(kingPosition -> king, attackingPiecePosition -> attackingPiece)
        )

        field.isCheck(Color.White) shouldBe true
      }
    }

    "finding the king" should {
      "return the position of the king of the given color" in {
        val kingPosition = PositionBaseImpl(4, 4)
        val king = new King(Color.White)
        val field = FieldBaseImpl(Map(kingPosition -> king))

        field.findKing(Color.White) shouldBe Some(kingPosition)
      }
    }

    "getting the current player" should {
      "return the color of the current player" in {
        val field = FieldBaseImpl(Map.empty, Color.Black)

        field.getCurrentPlayer() shouldBe Color.Black
      }
    }

    "checking if a position is valid" should {
      "return true for valid positions and false for invalid ones" in {
        val field = FieldBaseImpl(Map.empty)

        field.isPositionValid(PositionBaseImpl(1, 1)) shouldBe true
        field.isPositionValid(PositionBaseImpl(0, 5)) shouldBe false
        field.isPositionValid(PositionBaseImpl(9, 3)) shouldBe false
        field.isPositionValid(PositionBaseImpl(3, -1)) shouldBe false
      }
    }
  }
}
