package util.updater

import model.field.FieldInterface
import model.gamestate.GameStateInterface
import util.view.ViewInterface
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers.any
import util.updater.UpdaterBaseImpl

class UpdaterBaseImplTest extends AnyWordSpec with Matchers with MockitoSugar {
  "UpdaterBaseImpl" should {
    val updater = new UpdaterBaseImpl {}

    "addView" should {
      "add a view to the list of views" in {
        val view = mock[ViewInterface]
        updater.addView(view)
        updater.views should contain(view)
      }
    }

    "update" should {
      "call the update method on all views" in {
        val view1 = mock[ViewInterface]
        val view2 = mock[ViewInterface]
        val gameState = mock[GameStateInterface]

        updater.addView(view1)
        updater.addView(view2)

        updater.update(gameState)

        verify(view1).update(gameState)
        verify(view2).update(gameState)
      }
    }
  }
}
