package util

import model.Field
import util.Updater
import model.Position
import model.GameState

case class View(updater: Updater) {
  updater.addView(this);

  def update(gameState: GameState): Unit = throw NotImplementedError();

  def waitForInput(fakeInput: List[String] = List()): Unit =
    throw NotImplementedError();
}
