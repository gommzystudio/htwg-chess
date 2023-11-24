package model

case class Position(x: Int, y: Int) {
  def getCharX(): Char = {
    return (x + 'a' - 1).toChar
  }

  def flipPosition(): Position = {
    return Position(9 - x, 9 - y)
  }
}

object Position {
  def fromChar(charX: Char, y: Int): Position = new Position(charX - 'a' + 1, y)
}
