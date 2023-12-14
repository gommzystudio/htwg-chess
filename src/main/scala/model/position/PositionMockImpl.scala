package model.position

import model.position.PositionInterface

case class PositionMockImpl(x: Int, y: Int) extends PositionInterface(x, y) {
  def getCharX(): Char = {
    return 'a'
  }

  def flipPosition(): PositionInterface = {
    return this
  }

  def getX(): Int = {
    return x
  }

  def getY(): Int = {
    return y
  }
}

object PositionMockImpl {
  def fromChar(charX: Char, y: Int): PositionInterface =
    new PositionMockImpl(1, 1)
}
