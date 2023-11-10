package util

import model.Field
import controller.Controller
import model.Position

case class View(controller: Controller) {
  controller.addView(this);

  def update(field: Field) = {
    printField(field);
  }

  def printField(field: Field): Unit = {}

  def waitForInput(): Unit = {}
}
