import scala.annotation.switch
class Pawn(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♙" else "♟"
  }

  override def availableMoves(
      position: Position,
      field: Field
  ): Array[Position] = {
    if (c == Color.White) {
      val moves = Array[Position]()

      // Standard move
      if (field.getPiece(position.getX(), position.getY() + 1) == None) {
        moves :+ Position(position.getX(), position.getY() + 1)
      }

      // First move
      if (position.getY() == 2) {
        if (field.getPiece(position.getX(), position.getY() + 2) == None) {
          moves :+ Position(position.getX(), position.getY() + 2)
        }
      }

      // Capture
      if (field.getPiece(position.getX() + 1, position.getY() + 1) != None) {
        moves :+ Position(position.getX() + 1, position.getY() + 1)
      }

      if (field.getPiece(position.getX() - 1, position.getY() + 1) != None) {
        moves :+ Position(position.getX() - 1, position.getY() + 1)
      }

      return moves
    } else {
      val moves = Array[Position]()

      // Standard move
      if (field.getPiece(position.getX(), position.getY() - 1) == None) {
        moves :+ Position(position.getX(), position.getY() - 1)
      }

      // First move
      if (position.getY() == 7) {
        if (field.getPiece(position.getX(), position.getY() - 2) == None) {
          moves :+ Position(position.getX(), position.getY() - 2)
        }
      }

      // Capture
      if (field.getPiece(position.getX() + 1, position.getY() - 1) != None) {
        moves :+ Position(position.getX() + 1, position.getY() - 1)
      }

      if (field.getPiece(position.getX() - 1, position.getY() - 1) != None) {
        moves :+ Position(position.getX() - 1, position.getY() - 1)
      }

      return moves
    }
  }
}
