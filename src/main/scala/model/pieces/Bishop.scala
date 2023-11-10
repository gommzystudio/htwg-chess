package model.pieces

class Bishop(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♗" else "♝";
  }
}
