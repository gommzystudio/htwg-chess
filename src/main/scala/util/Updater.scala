package util

import model.Field
import util.View

case class Updater() {
  var views = List[View]()

  def addView(view: View) = {
    views = view :: views
  }

  def update(field: Field) = {
    for (view <- views) {
      view.update(field: Field)
    }
  }
}
