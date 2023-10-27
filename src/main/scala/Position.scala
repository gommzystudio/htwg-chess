case class Position(x: Int, y: Int) {
  def getCharX(): Char = {
    x match {
      case 1 => 'a'
      case 2 => 'b'
      case 3 => 'c'
      case 4 => 'd'
      case 5 => 'e'
      case 6 => 'f'
      case 7 => 'g'
      case 8 => 'h'
      case _ => ' '
    }
  }
}

object Position {
  def fromChar(charX: Char, y: Int): Position = new Position(charX - 'a' + 1, y)
}
