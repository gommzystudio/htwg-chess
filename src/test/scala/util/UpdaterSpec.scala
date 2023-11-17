package test.util

import org.scalatest.wordspec.AnyWordSpec
import util.{Updater, View}
import model.Field
import model.FieldFactory
import model.GameState

class UpdaterSpec extends AnyWordSpec {
  "An Updater" when {
    "created" should {
      "have no views" in {
        val updater = Updater()
        assert(updater.views.isEmpty)
      }
    }
    "a view is added" should {
      "contain the view" in {
        val updater = Updater()
        val view = new View(updater)
        assert(updater.views.contains(view))
      }
    }
    "update is called" should {
      "call update on all views" in {
        val updater = Updater()
        val field = FieldFactory.createInitialField()
        updater.update(GameState(field))
      }
    }
  }
}
