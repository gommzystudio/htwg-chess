package model.commands

import model.{Field, Position}
import model.pieces.Piece
import scala.util.{Try, Success, Failure}

final case class MoveCommand(from: Position, to: Position, field: Field)
    extends Command {
  val movedPiece: Option[Piece] = field.getPiece(from)
  val capturedPiece: Option[Piece] = field.getPiece(to)

  override def execute(): Try[Field] = {
    movedPiece.map { piece =>
      val newField = field.removePiece(from).setPiece(to, piece)
      Success(newField)
    }.getOrElse(Failure(new Exception("Kein Stück zum Bewegen")))
  }

  override def undo(): Try[Field] = {
    movedPiece.map { piece =>
      val newField = field.setPiece(from, piece)
      Success(newField)
    }.getOrElse(Failure(new Exception("Kein Stück zum Bewegen")))
  }
}
