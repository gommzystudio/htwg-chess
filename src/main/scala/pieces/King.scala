class King(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♔" else "♚"
  }
}
