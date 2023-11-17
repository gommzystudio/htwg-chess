package util

import model.Field
import util.Updater
import model.Position

case class View(updater: Updater) {
  updater.addView(this);

  def update(field: Field): Unit = throw NotImplementedError();

  def waitForInput(fakeInput: List[String] = List()): Unit =
    throw NotImplementedError();
}
