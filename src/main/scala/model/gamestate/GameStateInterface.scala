package model.gamestate

import model.commands.Command
import scala.util.Try
import model.field.FieldInterface
import util.color.*

import scala.xml.Elem
import play.api.libs.json.Json

trait GameStateInterface {
  def executeCommand(command: Command): GameStateInterface
  def undoCommand(): GameStateInterface
  def redoCommand(): GameStateInterface
  def getUndoStack(): List[Command]
  def getRedoStack(): List[Command]
  def getField(): FieldInterface
}
