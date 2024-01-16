package model.position

trait PositionInterface(x: Int, y: Int) {
  def getCharX(): Char

  def flipPosition(): PositionInterface

  def getX(): Int
  def getY(): Int
}
