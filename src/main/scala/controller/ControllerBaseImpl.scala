package controller

import model.commands.MoveCommand
import model.gamestate.GameStateInterface
import model.pieces.Piece
import model.position.PositionInterface
import scala.util.{Try, Success, Failure}
import util.fileio.FileIO
import util.updater.UpdaterBaseImpl
import com.google.inject.Inject
import util.view.ViewInterface

class ControllerBaseImpl @Inject() (
    var gameState: GameStateInterface,
    val fileIO: FileIO
) extends ControllerInterface
    with UpdaterBaseImpl {
  def load(path: String): Unit = Option(fileIO.read(path)).foreach { newState =>
    gameState = newState
    update(newState)
  }

  def save(path: String): Unit = fileIO.write(path, gameState)

  def addViewAndUpdate(view: ViewInterface): Unit = {
    addView(view)
    update(gameState)
  }

  def undoCommand(): Unit = {
    gameState = gameState.undoCommand()
    update(gameState)
  }

  def redoCommand(): Unit = {
    gameState = gameState.redoCommand()
    update(gameState)
  }

  def runMoveCommand(from: PositionInterface, to: PositionInterface): Unit = {
    Try(gameState.getField().getPiece(from).get) match {
      case Success(piece) if isValidMove(piece, from, to) =>
        executeMoveCommand(from, to, piece)
      case Success(_) => println("Ungültiger Zug")
      case Failure(_) => println("Kein Stück zum Bewegen")
    }
  }

  private def isValidMove(
      piece: Piece,
      from: PositionInterface,
      to: PositionInterface
  ): Boolean = {
    piece.availableMoves(from, gameState.getField()).contains(to) &&
    piece.color == gameState.getField().getCurrentPlayer()
  }

  private def executeMoveCommand(
      from: PositionInterface,
      to: PositionInterface,
      piece: Piece
  ): Unit = {
    val newGameState =
      gameState.executeCommand(new MoveCommand(from, to, gameState.getField()))

    if (newGameState.getField().isCheck(piece.color)) {
      println("Das sorgt für ein Schach")
    } else {
      gameState = newGameState
      update(gameState)
    }
  }

  def getGameState(): GameStateInterface = gameState
}
