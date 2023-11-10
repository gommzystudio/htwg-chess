package model

case class Position(x: Int, y: Int) {
  def getCharX(): Char = {
    return (x + 'a' - 1).toChar
  }
}

object Position {
  def fromChar(charX: Char, y: Int): Position = new Position(charX - 'a' + 1, y)
}
