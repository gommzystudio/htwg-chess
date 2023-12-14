package controller

import util.updater.UpdaterBaseImpl
import model.position.PositionInterface
import model.field.FieldFactory
import model.gamestate.GameStateBaseImpl
import model.pieces.Piece
import model.commands.MoveCommand
import scala.util.{Try, Success, Failure}
import util.view.ViewInterface
import util.updater.UpdaterBaseImpl

class ControllerBaseImpl() extends ControllerInterface with UpdaterBaseImpl {
  var gameState: GameStateBaseImpl = new GameStateBaseImpl(
    FieldFactory.createInitialField()
  );

  def addViewAndUpdate(view: ViewInterface) = {
    addView(view)
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

  def runMoveCommand(from: PositionInterface, to: PositionInterface): Unit = {
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

  def getGameSate(): GameStateBaseImpl = {
    gameState
  }
}
