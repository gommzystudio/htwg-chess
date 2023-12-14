package model.gamestate

import model.commands.Command
import scala.util.Try
import model.field.FieldInterface

trait GameStateInterface {
  def executeCommand(command: Command): GameStateInterface
  def undoCommand(): GameStateInterface
  def redoCommand(): GameStateInterface
  def getField(): FieldInterface
}
