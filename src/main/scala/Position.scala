case class Position(x: Int, y: Int) {
  def getX(): Int = this.x
  def getY(): Int = this.y

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
  def apply(x: Char, y: Int): Position = {
    val intX = x match {
      case 'a' => 1
      case 'b' => 2
      case 'c' => 3
      case 'd' => 4
      case 'e' => 5
      case 'f' => 6
      case 'g' => 7
      case 'h' => 8
      case _   => 0
    }
    Position(intX, y)
  }
}
