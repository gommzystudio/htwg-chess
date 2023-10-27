enum Color:
  case Black, White;

class Piece(c: Color) {
  val color: Color = c;

  def availableMoves(position: Position, field: Field): Array[Position] = {
    return Array.empty[Position];
  }

  def getSymbol(): String = {
    return null;
  }
}
