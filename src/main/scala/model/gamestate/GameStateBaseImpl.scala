package model.gamestate

import model.commands.Command
import model.field.FieldInterface
import model.field.FieldFactory
import scala.util.{Try, Success}

// Represents the state of a game, including the chessboard and command history.
class GameStateBaseImpl(
    val field: FieldInterface = FieldFactory.createInitialField(),
    undoStack: List[Command] = List(),
    redoStack: List[Command] = List()
) extends GameStateInterface {

  // Updates the game state, primarily used after executing, undoing, or redoing a command.
  private def updateState(
      newField: Try[FieldInterface],
      newUndoStack: List[Command],
      newRedoStack: List[Command] = redoStack
  ): GameStateBaseImpl =
    newField match {
      case Success(updatedField) =>
        new GameStateBaseImpl(updatedField, newUndoStack, newRedoStack)
      case _ => this
    }

  // Executes a command and updates the game state.
  def executeCommand(command: Command): GameStateBaseImpl =
    updateState(command.execute(), command :: undoStack, List())

  // Undoes the most recent command, updating the game state accordingly.
  def undoCommand(): GameStateBaseImpl =
    if (undoStack.nonEmpty)
      updateState(
        undoStack.head.undo(),
        undoStack.tail,
        undoStack.head :: redoStack
      )
    else this

  // Redoes the most recently undone command.
  def redoCommand(): GameStateBaseImpl =
    if (redoStack.nonEmpty)
      updateState(
        redoStack.head.execute(),
        redoStack.head :: undoStack,
        redoStack.tail
      )
    else this

  // Accessors for the current field, undo stack, and redo stack.
  def getField(): FieldInterface = field
  def getUndoStack(): List[Command] = undoStack
  def getRedoStack(): List[Command] = redoStack
}
