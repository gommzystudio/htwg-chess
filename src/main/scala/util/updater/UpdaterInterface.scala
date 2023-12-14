package util.updater

import util.view.ViewInterface
import model.gamestate.GameStateInterface

trait UpdaterInterface {
  def addView(view: ViewInterface): Unit
  def update(gameState: GameStateInterface): Unit
}
