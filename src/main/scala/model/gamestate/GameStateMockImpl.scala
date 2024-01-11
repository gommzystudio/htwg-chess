package model.gamestate

import model.commands.Command
import scala.util.Try
import model.field.FieldInterface
import model.gamestate.GameStateInterface
import model.field.FieldMockImpl
import util.color.*

case class GameStateMockImpl() extends GameStateInterface {
  def executeCommand(command: Command): GameStateInterface = this
  def undoCommand(): GameStateInterface = this
  def redoCommand(): GameStateInterface = this
  def getField(): FieldInterface = FieldMockImpl()
  def getUndoStack(): List[Command] = List()
  def getRedoStack(): List[Command] = List()
}
