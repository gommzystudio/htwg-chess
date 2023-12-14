package util.updater

import util.view.ViewInterface
import model.gamestate.GameStateInterface
import util.updater.UpdaterInterface

trait UpdaterMockImpl() extends UpdaterInterface {
  def addView(view: ViewInterface): Unit = {}
  def update(gameState: GameStateInterface): Unit = {}
}
