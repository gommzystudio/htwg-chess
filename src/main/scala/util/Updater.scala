package util

import model.Field
import util.View
import model.GameState

case class Updater() {
  var views = List[View]()

  def addView(view: View) = {
    views = view :: views
  }

  def update(gameState: GameState) = {
    for (view <- views) {
      view.update(gameState)
    }
  }
}
