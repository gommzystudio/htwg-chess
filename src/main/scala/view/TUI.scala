package view

import model.field.FieldInterface
import controller.ControllerBaseImpl
import controller.ControllerInterface
import model.position.PositionBaseImpl
import util.view.ViewInterface
import model.gamestate.GameStateInterface
import model.commands.MoveCommand
import util.updater.UpdaterInterface

import scala.util.{Try, Success, Failure}

class TUI(controller: ControllerInterface)
    extends ViewInterface(controller.asInstanceOf[UpdaterInterface]) {
  override def update(gameState: GameStateInterface): Unit = {
    printField(gameState.getField());
  }

  def printField(field: FieldInterface): Unit = {
    println("\u001b[H\u001b[2J")
    println("  a b c d e f g h")
    println("  ---------------")

    for (y <- 8 to 1 by -1) {
      print(y + "|")
      for (x <- 1 to 8 by 1) {
        field.getPiece(PositionBaseImpl(x, y)) match {
          case Some(piece) => print(piece.getSymbol() + " ")
          case None        => print("  ")
        }
      }
      println("|" + y)
    }

    println("  ---------------")
    println("  a b c d e f g h")
  }

  override def startView(): Unit = {
    controller.addViewAndUpdate(this);

    waitForInput();
  }

  def waitForInput(fakeInput: List[String] = List()): Unit = {
    println("Enter move (e.g. a2a3): ");
    val input =
      if (fakeInput.isEmpty) scala.io.StdIn.readLine(">> ")
      else fakeInput.head;

    if (input == "exit") {
      return;
    }
    if (input == "undo")
      controller.undoCommand();
    else if (input == "redo")
      controller.redoCommand();
    else if (input.length() == 4) {
      val from =
        PositionBaseImpl.fromChar(input.charAt(0), input.charAt(1).asDigit)
      val to =
        PositionBaseImpl.fromChar(input.charAt(2), input.charAt(3).asDigit)
      controller.runMoveCommand(from, to)
    }

    if (fakeInput.isEmpty) waitForInput() else waitForInput(fakeInput.tail);
  }
}
