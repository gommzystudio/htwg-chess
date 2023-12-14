package model.position

import model.position.PositionInterface

case class PositionBaseImpl(x: Int, y: Int) extends PositionInterface(x, y) {
  def getCharX(): Char = {
    return (x + 'a' - 1).toChar
  }

  def flipPosition(): PositionBaseImpl = {
    return PositionBaseImpl(9 - x, 9 - y)
  }

  def getX(): Int = {
    return x
  }

  def getY(): Int = {
    return y
  }
}

object PositionBaseImpl {
  def fromChar(charX: Char, y: Int): PositionInterface =
    new PositionBaseImpl(charX - 'a' + 1, y)
}
