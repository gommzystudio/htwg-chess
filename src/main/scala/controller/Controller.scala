package controller

import util.Updater
import model.Field
import model.Position
import model.FieldFactory
import model.GameState
import model.commands.Command
import model.commands.MoveCommand

class Controller() extends Updater {
  val commands: List[Command] = List[Command]();
  var gameState: GameState = new GameState(FieldFactory.createInitialField());

  def startGame() = {
    update(gameState);
  }

  def runMoveCommand(from: Position, to: Position): Unit = {
    runCommand(new MoveCommand(from, to, gameState));
  }

  def runCommand(command: Command): Unit = {
    gameState = command.execute();
    commands :+ command;
    update(gameState);
  }
}
