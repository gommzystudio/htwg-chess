package model.pieces

class Knight(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♘" else "♞"
  }
}
