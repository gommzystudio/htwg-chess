package controller

import util.Updater
import model.Field
import model.Position
import model.FieldFactory
import model.GameState
import model.pieces.Piece
import model.commands.MoveCommand
import scala.util.{Try, Success, Failure}
import util.View

class Controller() extends Updater {
  var gameState: GameState = new GameState(FieldFactory.createInitialField());

  override def addView(view: View) = {
    views = view :: views
    update(gameState)
  }

  def undoCommand() = {
    gameState = gameState.undoCommand();
    update(gameState);
  }

  def redoCommand() = {
    gameState = gameState.redoCommand();
    update(gameState);
  }

  def runMoveCommand(from: Position, to: Position): Unit = {
    val pieceTry: Try[Piece] = Try(gameState.getField().getPiece(from).get)
    pieceTry match {
      case Success(piece) =>
        if (!piece.availableMoves(from, gameState.getField()).contains(to)) {
          println("Ungültiger Zug")
          return
        }
        gameState = gameState.executeCommand(
          new MoveCommand(from, to, gameState.getField())
        )
        update(gameState)
      case Failure(_) =>
        println("Kein Stück zum Bewegen")
    }
  }
}
