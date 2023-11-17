package test.util

import org.scalatest.wordspec.AnyWordSpec
import controller.Controller
import util.View
import model.Field
import model.FieldFactory
import model.GameState

class ViewSpec extends AnyWordSpec {
  "A View" when {
    "created" should {
      "subscribe to a controller" in {
        val controller = new Controller()
        val view = new View(controller)
        assert(controller.views.contains(view) == true)
      }
    }
    "update is called" should {
      "throw NotImplementedError" in {
        val controller = new Controller()
        val view = new View(controller)
        assertThrows[NotImplementedError] {
          view.update(GameState(FieldFactory.createInitialField()))
        }
      }
    }
    "waitForInput is called" should {
      "throw NotImplementedError" in {
        val controller = new Controller()
        val view = new View(controller)
        assertThrows[NotImplementedError] {
          view.waitForInput(List("a2a3", "exit"))
        }
      }
    }
  }
}
