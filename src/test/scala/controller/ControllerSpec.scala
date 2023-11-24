package test.controller

import org.scalatest.wordspec.AnyWordSpec
import controller.Controller
import view.TUI
import model.{Field, Position}

class ControllerSpec extends AnyWordSpec {
  "A Controller" when {
    "startGame is called" should {
      "create a new field and update views" in {
        val controller = new Controller()
        controller.startGame()
        assert(controller.gameState != null)
      }
    }
    "movePiece is called" should {
      "move a piece and update views" in {
        val controller = new Controller()
        val view = new TUI(controller)
        controller.addView(view)
        controller.startGame()
        val from = Position(1, 1)
        val to = Position(1, 2)
        val initinalState = controller.gameState
        controller.runMoveCommand(from, to)
        assert(controller.gameState != initinalState)
      }
    }
  }
}
