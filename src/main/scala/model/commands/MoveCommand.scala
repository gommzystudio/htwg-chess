package model.commands

import model.position.PositionInterface
import model.field.FieldInterface
import model.pieces.Piece
import scala.util.{Try, Success, Failure}
import model.commands.Command

final case class MoveCommand(
    from: PositionInterface,
    to: PositionInterface,
    field: FieldInterface
) extends Command {
  val movedPiece: Option[Piece] = field.getPiece(from)
  val capturedPiece: Option[Piece] = field.getPiece(to)

  override def execute(): Try[FieldInterface] = {
    movedPiece
      .map { piece =>
        val newField = field.removePiece(from).setPiece(to, piece).flipPlayer()
        Success(newField)
      }
      .getOrElse(Failure(new Exception("Kein Stück zum Bewegen")))
  }

  override def undo(): Try[FieldInterface] = {
    movedPiece
      .map { piece =>
        val newField = field.setPiece(from, piece).flipPlayer()
        Success(newField)
      }
      .getOrElse(Failure(new Exception("Kein Stück zum Bewegen")))
  }
}
