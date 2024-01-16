package view

import controller.ControllerInterface
import model.field.FieldInterface
import model.gamestate.GameStateInterface
import model.position.PositionBaseImpl
import util.color.Color
import util.updater.UpdaterInterface
import util.view.ViewInterface

import scala.util.{Failure, Success, Try}

class TUI(controller: ControllerInterface)
  extends ViewInterface(controller.asInstanceOf[UpdaterInterface]) {
  override def startView(): Unit = {
    controller.addViewAndUpdate(this)
    waitForInput()
  }

  def waitForInput(): Unit = {
    println("Enter move (e.g. a2a3): ");
    val input = scala.io.StdIn.readLine(">> ");

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

    waitForInput()
  }

  override def update(gameState: GameStateInterface): Unit = printField(gameState.getField())

  def printField(field: FieldInterface): Unit = {
    clearScreen()
    printBoard(field)
    printCheckStatus(field)
    printWinner(field)
  }

  private def clearScreen(): Unit = println("\u001b[H\u001b[2J")

  private def printBoard(field: FieldInterface): Unit = {
    val boardHeader = "  a b c d e f g h"
    val boardDelimiter = "  ---------------"

    println(boardHeader)
    println(boardDelimiter)

    for (y <- 8 to 1 by -1) {
      print(y + "|")
      for (x <- 1 to 8 by 1) {
        field.getPiece(PositionBaseImpl(x, y)) match {
          case Some(piece) => print(piece.getSymbol() + " ")
          case None => print("  ")
        }
      }
      println("|" + y)
    }

    println(boardDelimiter)
    println(boardHeader)
  }

  private def printCheckStatus(field: FieldInterface): Unit = {
    if (field.isCheck(Color.White)) println("Weiß ist im Schach!")
    else if (field.isCheck(Color.Black)) println("Schwarz ist im Schach!")
  }

  private def printWinner(field: FieldInterface): Unit = {
    field.isCheckMate().foreach {
      case Color.White => println("Schwarz hat gewonnen!")
      case Color.Black => println("Weiß hat gewonnen!")
    }
  }
}
