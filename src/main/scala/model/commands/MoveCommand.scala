package model.commands

import model.field.FieldInterface
import model.pieces.Piece
import model.position.PositionInterface

import scala.util.{Failure, Success, Try}

final case class MoveCommand(
    from: PositionInterface,
    to: PositionInterface,
    field: FieldInterface
) extends Command {
  private val movedPiece: Option[Piece] = field.getPiece(from)
  private val capturedPiece: Option[Piece] = field.getPiece(to)

  override def execute(): Try[FieldInterface] = {
    movedPiece match {
      case Some(piece) =>
        Try {
          field.removePiece(from).setPiece(to, piece).flipPlayer()
        }
      case None =>
        Failure(new NoSuchElementException("Kein Stück am Startfeld vorhanden"))
    }
  }

  override def undo(): Try[FieldInterface] = {
    movedPiece match {
      case Some(piece) =>
        Try {
          val fieldWithMovedBack = field.removePiece(to).setPiece(from, piece)
          capturedPiece.fold(fieldWithMovedBack)(
            fieldWithMovedBack.setPiece(to, _)
          )
        }
      case None =>
        Failure(
          new IllegalStateException(
            "Rückgängig machen nicht möglich, da kein Stück bewegt wurde"
          )
        )
    }
  }
}
