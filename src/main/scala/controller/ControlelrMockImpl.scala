package controller

import model.position.PositionInterface
import util.view.ViewInterface
import model.gamestate.GameStateMockImpl
import model.gamestate.GameStateInterface

class ControllerMockImpl() extends ControllerInterface {
  def undoCommand(): Unit = {}
  def redoCommand(): Unit = {}
  def addViewAndUpdate(view: ViewInterface): Unit = {}
  def runMoveCommand(from: PositionInterface, to: PositionInterface): Unit = {}
  def getGameSate(): GameStateInterface = new GameStateMockImpl()
  def load(path: String): Unit = {}
  def save(path: String): Unit = {}
}
