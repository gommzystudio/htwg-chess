package controller

import util.Updater
import model.Field
import model.Position

class Controller() extends Updater {
  var currentField: Field = null;

  def startGame() = {
    currentField = new Field();
    update(currentField);
  }

  def movePiece(from: Position, to: Position) = {
    currentField = currentField.movePiece(from, to);
    update(currentField);
  }
}
