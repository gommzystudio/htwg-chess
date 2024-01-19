package view

import controller.ControllerInterface
import model.field.FieldInterface
import model.gamestate.GameStateInterface
import model.position.PositionBaseImpl
import util.color.Color
import util.updater.UpdaterInterface
import util.view.ViewInterface

import scala.util.{Failure, Success, Try}

// TUI (Text User Interface) class to interact with the game via text.
class TUI(controller: ControllerInterface)
    extends ViewInterface(controller.asInstanceOf[UpdaterInterface]) {

  // Starts the view and waits for user input.
  override def startView(): Unit = {
    controller.addViewAndUpdate(this)
    waitForInput()
  }

  // Waits for the user to enter a command, processes it, and acts accordingly.
  def waitForInput(): Unit = {
    println("Enter move (e.g. a2a3): ")
    val input = scala.io.StdIn.readLine(">> ")

    if (input == "exit") {
      return
    }

    if (input == "undo")
      controller.undoCommand()
    else if (input == "redo")
      controller.redoCommand()
      
    // Processes a move command if the input length is 4 characters (e.g., a2a4).
    else if (input.length() == 4) {
      val from =
        PositionBaseImpl.fromChar(input.charAt(0), input.charAt(1).asDigit)
      val to =
        PositionBaseImpl.fromChar(input.charAt(2), input.charAt(3).asDigit)
      controller.runMoveCommand(from, to)
    }

    // Waits for more input after processing the current input.
    waitForInput()
  }

  // Updates the view based on the current game state.
  override def update(gameState: GameStateInterface): Unit = printField(
    gameState.getField()
  )

  // Prints the current state of the field.
  def printField(field: FieldInterface): Unit = {
    clearScreen()
    printBoard(field)
    printCheckStatus(field)
    printWinner(field)
  }

  // Clears the screen for a fresh view.
  private def clearScreen(): Unit = println("\u001b[H\u001b[2J")

  // Prints the game board.
  private def printBoard(field: FieldInterface): Unit = {
    // Printing board headers.
    val boardHeader = "  a b c d e f g h"
    val boardDelimiter = "  ---------------"
    println(boardHeader)
    println(boardDelimiter)

    // Constructing and printing the board with pieces.
    val boardString = (8 to 1 by -1)
      .map { y =>
        val row = (1 to 8).map { x =>
          field.getPiece(PositionBaseImpl(x, y)) match {
            case Some(piece) => piece.getSymbol() + " "
            case None        => "  "
          }
        }.mkString
        s"$y|$row|$y"
      }
      .mkString("\n")

    println(boardString)
    println(boardDelimiter)
    println(boardHeader)
  }

  // Prints the check status of each player.
  private def printCheckStatus(field: FieldInterface): Unit = {
    if (field.isCheck(Color.White)) println("Weiß ist im Schach!")
    else if (field.isCheck(Color.Black)) println("Schwarz ist im Schach!")
  }

  // Announces the winner if there is a checkmate.
  private def printWinner(field: FieldInterface): Unit = {
    field.isCheckMate().foreach {
      case Color.White => println("Schwarz hat gewonnen!")
      case Color.Black => println("Weiß hat gewonnen!")
    }
  }
}
