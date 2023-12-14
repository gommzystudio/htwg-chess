package util

import model.Field
import util.Updater
import model.Position
import model.GameState

trait View(updater: Updater) {
  def update(gameState: GameState): Unit = throw NotImplementedError();

  def startView(): Unit = throw NotImplementedError();
}
