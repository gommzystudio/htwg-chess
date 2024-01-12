package controller

import util.updater.UpdaterBaseImpl
import model.position.PositionInterface
import model.field.FieldFactory
import model.pieces.Piece
import model.commands.MoveCommand
import scala.util.{Try, Success, Failure}
import util.view.ViewInterface
import util.updater.UpdaterBaseImpl
import util.color.Color
import com.google.inject.Inject
import model.gamestate.GameStateInterface
import util.fileio.FileIO

class ControllerBaseImpl @Inject() (
    var gameState: GameStateInterface,
    val fileIO: FileIO
) extends ControllerInterface
    with UpdaterBaseImpl {
  def load(path: String) = {
    val newGameState = fileIO.read(path)
    if (newGameState != null) {
      gameState = newGameState
      update(newGameState)
    }
  }

  def save(path: String) = {
    fileIO.write(path, gameState)
  }

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
        val playerColor = piece.color

        val newGameState = gameState.executeCommand(
          new MoveCommand(from, to, gameState.getField())
        )

        if (newGameState.getField().isCheck(playerColor)) {
          println("Das sorgt für ein Schach")
          return
        }

        gameState = newGameState

        update(gameState)
      case Failure(_) =>
        println("Kein Stück zum Bewegen")
    }
  }

  def getGameSate(): GameStateInterface = {
    gameState
  }
}
