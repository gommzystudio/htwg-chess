package model

import model.commands.Command
import model.commands.MoveCommand
import scala.util.Success
import scala.util.Failure
import scala.util.Try

class GameState(val field: Field, undoStack:List[Command] = List(), redoStack: List[Command] = List()) {
  def executeCommand(command: Command): GameState = {
    val newField: Try[Field] = command.execute()
    val newUndoStack = command :: undoStack
    
    newField match {
      case Success(field) => {
        return new GameState(field, newUndoStack, List())
      }
      case Failure(exception) => {
        return this
      }
    }
  }

  def undoCommand(): GameState = {
    if (undoStack.isEmpty) {
      return this
    }
    val command = undoStack.head
    val newField = command.undo()
    val newUndoStack = undoStack.tail
    val newRedoStack = command :: redoStack
    
    newField match {
      case Success(field) => {
        return new GameState(field, newUndoStack, newRedoStack)
      }
      case Failure(exception) => {
        return this
      }
    }
  }

  def redoCommand(): GameState = {
    if (redoStack.isEmpty) {
      return this
    }
    val command = redoStack.head
    val newField = command.execute()
    val newUndoStack = command :: undoStack
    val newRedoStack = redoStack.tail
    
    newField match {
      case Success(field) => {
        return new GameState(field, newUndoStack, newRedoStack)
      }
      case Failure(exception) => {
        return this
      }
    }
  }

  def getField(): Field = {
    return field
  }
}
