package model.commands

import model.{GameState, Position}
import model.pieces.Piece

final case class MoveCommand(from: Position, to: Position, gamestate: GameState)
    extends Command {
  var movedPiece: Option[Piece] = gamestate.getField().getPiece(to)
  var capturedPiece: Option[Piece] = gamestate.getField().getPiece(from)

  def execute(): GameState = {
    return gamestate.updateField(
      gamestate
        .getField()
        .removePiece(from)
        .setPiece(to, capturedPiece.get)
    )
  }
  def undo(): GameState = {
    return gamestate.updateField(
      gamestate
        .getField()
        .removePiece(to)
        .setPiece(from, movedPiece.get)
    )
  }
  def redo(): GameState = {
    return execute()
  }
}
