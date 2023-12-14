package util.view

import util.updater.UpdaterMockImpl
import model.gamestate.GameStateInterface
import util.view.ViewInterface

abstract class ViewMockImpl(updater: UpdaterMockImpl)
    extends ViewInterface(updater) {
  def update(gameState: GameStateInterface): Unit
  def startView(): Unit
}
