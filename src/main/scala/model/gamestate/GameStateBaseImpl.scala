package model.gamestate

import model.commands.Command
import model.field.FieldInterface
import model.field.FieldFactory
import scala.util.{Try, Success}
import util.color.Color
import com.google.inject.Inject

class GameStateBaseImpl(
    val field: FieldInterface = FieldFactory.createInitialField(),
    undoStack: List[Command] = List(),
    redoStack: List[Command] = List()
) extends GameStateInterface {

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

  def executeCommand(command: Command): GameStateBaseImpl =
    updateState(command.execute(), command :: undoStack, List())

  def undoCommand(): GameStateBaseImpl =
    if (undoStack.isEmpty) this
    else
      updateState(
        undoStack.head.undo(),
        undoStack.tail,
        undoStack.head :: redoStack
      )

  def redoCommand(): GameStateBaseImpl =
    if (redoStack.isEmpty) this
    else
      updateState(
        redoStack.head.execute(),
        redoStack.head :: undoStack,
        redoStack.tail
      )

  def getField(): FieldInterface = field

  def getUndoStack(): List[Command] = undoStack

  def getRedoStack(): List[Command] = redoStack
}
