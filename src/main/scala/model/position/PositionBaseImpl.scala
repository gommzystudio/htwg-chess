package model.position

import model.position.PositionInterface
import com.google.inject.Inject

case class PositionBaseImpl(x: Int, y: Int) extends PositionInterface(x, y) {
  def getCharX(): Char = (x + 'a' - 1).toChar

  def flipPosition(): PositionBaseImpl = PositionBaseImpl(9 - x, 9 - y)

  def getX(): Int = x

  def getY(): Int = y
}

object PositionBaseImpl {
  def fromChar(charX: Char, y: Int): PositionInterface =
    new PositionBaseImpl(charX - 'a' + 1, y)
}
