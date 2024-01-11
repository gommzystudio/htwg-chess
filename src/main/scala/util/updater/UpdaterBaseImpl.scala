package util.updater

import util.view.ViewInterface
import model.gamestate.GameStateInterface
import util.updater.UpdaterInterface
import com.google.inject.Inject

trait UpdaterBaseImpl extends UpdaterInterface {
  var views = List[ViewInterface]()

  def addView(view: ViewInterface) = {
    views = view :: views
  }

  def update(gameState: GameStateInterface) = {
    for (view <- views) {
      view.update(gameState)
    }
  }
}
