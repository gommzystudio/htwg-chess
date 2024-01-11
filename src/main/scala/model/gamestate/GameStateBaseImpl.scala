package model.gamestate

import model.commands.Command
import model.commands.MoveCommand
import scala.util.Success
import scala.util.Failure
import scala.util.Try
import model.field.FieldInterface
import model.gamestate.GameStateInterface
import util.color.*
import com.google.inject.Inject
import model.field.FieldFactory
import scala.xml.Elem
import play.api.libs.json.Json

class GameStateBaseImpl(
    val field: FieldInterface = FieldFactory.createInitialField(),
    undoStack: List[Command] = List(),
    redoStack: List[Command] = List()
) extends GameStateInterface {
  def executeCommand(command: Command): GameStateBaseImpl = {
    val newField: Try[FieldInterface] = command.execute()
    val newUndoStack = command :: undoStack

    newField match {
      case Success(field) => {
        return new GameStateBaseImpl(
          field,
          newUndoStack,
          List()
        )
      }
      case Failure(exception) => {
        return this
      }
    }
  }

  def getUndoStack(): List[Command] = {
    return undoStack
  }

  def getRedoStack(): List[Command] = {
    return redoStack
  }

  def undoCommand(): GameStateBaseImpl = {
    if (undoStack.isEmpty) {
      return this
    }
    val command = undoStack.head
    val newField = command.undo()
    val newUndoStack = undoStack.tail
    val newRedoStack = command :: redoStack

    newField match {
      case Success(field) => {
        return new GameStateBaseImpl(
          field,
          newUndoStack,
          newRedoStack
        )
      }
      case Failure(exception) => {
        return this
      }
    }
  }

  def redoCommand(): GameStateBaseImpl = {
    if (redoStack.isEmpty) {
      return this
    }
    val command = redoStack.head
    val newField = command.execute()
    val newUndoStack = command :: undoStack
    val newRedoStack = redoStack.tail

    newField match {
      case Success(field) => {
        return new GameStateBaseImpl(
          field,
          newUndoStack,
          newRedoStack
        )
      }
      case Failure(exception) => {
        return this
      }
    }
  }

  def getField(): FieldInterface = {
    return field
  }
}
