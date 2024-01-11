package model.gamestate

import model.commands.Command
import scala.util.Try
import model.field.FieldInterface
import util.color.*

trait GameStateInterface {
  def executeCommand(command: Command): GameStateInterface
  def undoCommand(): GameStateInterface
  def redoCommand(): GameStateInterface
  def getField(): FieldInterface
}
