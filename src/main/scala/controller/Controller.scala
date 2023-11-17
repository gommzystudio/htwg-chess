package controller

import util.Updater
import model.Field
import model.Position
import model.FieldFactory
import model.GameState

class Controller() extends Updater {
  var gameState: GameState = new GameState(FieldFactory.createInitialField());

  def startGame() = {
    update(gameState);
  }

  def movePiece(from: Position, to: Position) = {
    gameState = gameState.movePiece(from, to);
    update(gameState);
  }
}
