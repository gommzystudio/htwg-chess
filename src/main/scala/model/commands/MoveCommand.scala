package model.commands

import model.field.FieldInterface
import model.pieces.Piece
import model.position.PositionInterface

import scala.util.{Failure, Success, Try}

// The MoveCommand class represents a command to move a piece on a chessboard.
final case class MoveCommand(
    from: PositionInterface, 
    to: PositionInterface, 
    field: FieldInterface 
) extends Command {
  private val movedPiece: Option[Piece] = field.getPiece(from)
  private val capturedPiece: Option[Piece] = field.getPiece(to)

  // Executes the move.
  override def execute(): Try[FieldInterface] = {
    movedPiece match {
      // If there is a piece at the starting position, move it to the target position.
      case Some(piece) =>
        Try {
          field.removePiece(from).setPiece(to, piece).flipPlayer()
        }
      // If no piece is found at the starting position, return an error.
      case None =>
        Failure(new NoSuchElementException("Kein Stück am Startfeld vorhanden"))
    }
  }

  // Undoes the move.
  override def undo(): Try[FieldInterface] = {
    movedPiece match {
      // If a piece was moved, move it back to its original position.
      case Some(piece) =>
        Try {
          val fieldWithMovedBack = field.removePiece(to).setPiece(from, piece)
          // If a piece was captured, put it back on the board.
          capturedPiece.fold(fieldWithMovedBack)(
            fieldWithMovedBack.setPiece(to, _)
          )
        }
      // If no piece was moved, return an error stating undo is not possible.
      case None =>
        Failure(
          new IllegalStateException(
            "Rückgängig machen nicht möglich, da kein Stück bewegt wurde"
          )
        )
    }
  }
}
