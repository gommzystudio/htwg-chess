package model.commands

import model.field.FieldInterface
import model.pieces.Piece
import model.position.PositionInterface
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.util.{Failure, Success}

class MoveCommandTest extends AnyWordSpec with Matchers with MockitoSugar {

  "MoveCommand" should {

    "successfully execute move" in {
      val from = mock[PositionInterface]
      val to = mock[PositionInterface]
      val field = mock[FieldInterface]
      val piece = mock[Piece]

      when(field.getPiece(from)).thenReturn(Some(piece))
      when(field.removePiece(from)).thenReturn(field)
      when(field.setPiece(to, piece)).thenReturn(field)
      when(field.flipPlayer()).thenReturn(field)

      val moveCommand = MoveCommand(from, to, field)

      moveCommand.execute() shouldBe a[Success[_]]

      verify(field).removePiece(from)
      verify(field).setPiece(to, piece)
      verify(field).flipPlayer()
    }

    "fail to execute move when no piece is present at the starting position" in {
      val from = mock[PositionInterface]
      val to = mock[PositionInterface]
      val field = mock[FieldInterface]

      when(field.getPiece(from)).thenReturn(None)

      val moveCommand = MoveCommand(from, to, field)

      moveCommand.execute() shouldBe a[Failure[_]]
    }

    "successfully undo move" in {
      val from = mock[PositionInterface]
      val to = mock[PositionInterface]
      val field = mock[FieldInterface]
      val piece = mock[Piece]
      val capturedPiece = mock[Piece]

      when(field.getPiece(from)).thenReturn(Some(piece))
      when(field.getPiece(to)).thenReturn(Some(capturedPiece))
      when(field.removePiece(to)).thenReturn(field)
      when(field.setPiece(from, piece)).thenReturn(field)
      when(field.setPiece(to, capturedPiece)).thenReturn(field)

      val moveCommand = MoveCommand(from, to, field)

      moveCommand.undo() shouldBe a[Success[_]]

      verify(field).removePiece(to)
      verify(field).setPiece(from, piece)
      verify(field).setPiece(to, capturedPiece)
    }

    "fail to undo move when no piece was moved originally" in {
      val from = mock[PositionInterface]
      val to = mock[PositionInterface]
      val field = mock[FieldInterface]
      when(field.getPiece(from)).thenReturn(None)

      val moveCommand = MoveCommand(from, to, field)

      moveCommand.undo() shouldBe a[Failure[_]]
    }

  }
}
