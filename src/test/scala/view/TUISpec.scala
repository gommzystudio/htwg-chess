package test.view

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import controller.Controller
import view.TUI
import model.{Field, Position}
import model.pieces.{Color, Pawn}
import org.scalatest.wordspec.AnyWordSpec
import model.FieldFactory

class TUISpec extends AnyWordSpec {
  "A TUI" when {
    "created" should {
      "be able to print a field" in {
        val controller = new Controller()
        controller.startGame()
        val view = new TUI(controller)
        view.printField(FieldFactory.createInitialField())
      }
      "be able to wait for input and move a piece" in {
        val controller = new Controller()
        controller.startGame()
        val view = new TUI(controller)
        view.waitForInput(List("a2a3", "exit"))
        assert(
          controller.gameState
            .getField()
            .pieces
            .get(new Position(1, 3))
            .get != null
        )
      }
    }
  }
}
