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

// The controller base implementation class, handling game state and file operations.
class ControllerBaseImpl @Inject() (
    var gameState: GameStateInterface,
    val fileIO: FileIO
) extends ControllerInterface
    with UpdaterBaseImpl {

  // Loads game state from a file.
  def load(path: String): Unit = {
    Option(fileIO.read(path)).foreach { newState =>
      gameState = newState
      update(newState)
    }
  }

  // Saves the current game state to a file.
  def save(path: String): Unit = fileIO.write(path, gameState)

  // Adds a view to the controller and updates it with the current game state.
  def addViewAndUpdate(view: ViewInterface): Unit = {
    addView(view)
    update(gameState)
  }

  // Reverts the game state to the previous state (undo).
  def undoCommand(): Unit = {
    gameState = gameState.undoCommand()
    update(gameState)
  }

  // Re-applies the last undone command (redo).
  def redoCommand(): Unit = {
    gameState = gameState.redoCommand()
    update(gameState)
  }

  // Runs a move command if valid.
  def runMoveCommand(from: PositionInterface, to: PositionInterface): Unit = {
    Try(gameState.getField().getPiece(from).get) match {
      case Success(piece) if isValidMove(piece, from, to) =>
        executeMoveCommand(from, to, piece) // Execute the move if valid.
      case Success(_) => println("Ungültiger Zug")
      case Failure(_) => println("Kein Stück zum Bewegen")
    }
  }

  // Checks if a move is valid.
  private def isValidMove(
      piece: Piece,
      from: PositionInterface,
      to: PositionInterface
  ): Boolean = {
    // Check if the move is in the piece's available moves and the piece belongs to the current player.
    piece.availableMoves(from, gameState.getField()).contains(to) &&
    piece.color == gameState.getField().getCurrentPlayer()
  }

  // Executes a move command and updates the game state.
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

  // Returns the current game state.
  def getGameState(): GameStateInterface = gameState
}
