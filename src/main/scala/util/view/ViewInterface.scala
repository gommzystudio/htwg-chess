package util.view

import util.updater.UpdaterInterface
import model.gamestate.GameStateInterface

trait ViewInterface(updater: UpdaterInterface) {
  def update(gameState: GameStateInterface): Unit = throw NotImplementedError();

  def startView(): Unit = throw NotImplementedError();
}
