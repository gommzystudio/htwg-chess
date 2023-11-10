package view

import model.Field
import controller.Controller
import model.Position
import util.View

class TUI(controller: Controller) extends View(controller) {
  override def update(field: Field): Unit = {
    printField(field);
  }

  def printField(field: Field): Unit = {
    println("\u001b[H\u001b[2J")
    println("  a b c d e f g h")
    println("  ---------------")

    for (y <- 8 to 1 by -1) {
      print(y + "|")
      for (x <- 1 to 8 by 1) {
        field.pieces.get(Position(x, y)) match {
          case Some(piece) => print(piece.getSymbol() + " ")
          case None        => print("  ")
        }
      }
      println("|" + y)
    }

    println("  ---------------")
    println("  a b c d e f g h")
  }

  override def waitForInput(fakeInput: List[String] = List()): Unit = {
    println("Enter move (e.g. a2a3): ");
    val input =
      if (fakeInput.isEmpty) scala.io.StdIn.readLine() else fakeInput.head;

    if (input == "exit") {
      return;
    }

    val from = Position.fromChar(input.charAt(0), input.charAt(1).asDigit);
    val to = Position.fromChar(input.charAt(2), input.charAt(3).asDigit);

    controller.movePiece(from, to);

    waitForInput(fakeInput.tail);
  }
}
