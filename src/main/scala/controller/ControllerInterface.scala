package controller

import model.position.PositionInterface
import util.view.ViewInterface
import scala.util.Try
import model.gamestate.GameStateInterface
import util.updater.UpdaterInterface

trait ControllerInterface {
  def undoCommand(): Unit
  def redoCommand(): Unit
  def runMoveCommand(from: PositionInterface, to: PositionInterface): Unit
  def getGameSate(): GameStateInterface
  def addViewAndUpdate(view: ViewInterface): Unit
  def load(): Unit
}
