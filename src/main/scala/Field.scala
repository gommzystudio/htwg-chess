import scala.collection.immutable.Map

case class Field(p: Map[Position, Piece]) {
  val pieces = p match {
    case null =>
      Map(
        // Weiß
        Position(1, 1) -> Rook(Color.White),
        Position(2, 1) -> Knight(Color.White),
        Position(3, 1) -> Bishop(Color.White),
        Position(4, 1) -> Queen(Color.White),
        Position(5, 1) -> King(Color.White),
        Position(6, 1) -> Bishop(Color.White),
        Position(7, 1) -> Knight(Color.White),
        Position(8, 1) -> Rook(Color.White),
        Position(1, 2) -> Pawn(Color.White),
        Position(2, 2) -> Pawn(Color.White),
        Position(3, 2) -> Pawn(Color.White),
        Position(4, 2) -> Pawn(Color.White),
        Position(5, 2) -> Pawn(Color.White),
        Position(6, 2) -> Pawn(Color.White),
        Position(7, 2) -> Pawn(Color.White),
        Position(8, 2) -> Pawn(Color.White),

        // Schwarz
        Position(1, 8) -> Rook(Color.Black),
        Position(2, 8) -> Knight(Color.Black),
        Position(3, 8) -> Bishop(Color.Black),
        Position(4, 8) -> Queen(Color.Black),
        Position(5, 8) -> King(Color.Black),
        Position(6, 8) -> Bishop(Color.Black),
        Position(7, 8) -> Knight(Color.Black),
        Position(8, 8) -> Rook(Color.Black),
        Position(1, 7) -> Pawn(Color.Black),
        Position(2, 7) -> Pawn(Color.Black),
        Position(3, 7) -> Pawn(Color.Black),
        Position(4, 7) -> Pawn(Color.Black),
        Position(5, 7) -> Pawn(Color.Black),
        Position(6, 7) -> Pawn(Color.Black),
        Position(7, 7) -> Pawn(Color.Black),
        Position(8, 7) -> Pawn(Color.Black)
      )
    case _ => p
  }

  def checkLegality(piece: Piece, from: Position, to: Position): Boolean = {
    val moves = piece.availableMoves(from, this)
    println(from.toString() + " -> " + to.toString());
    println(moves.length.toString() + " moves available:")
    for (move <- moves) {
      println(move)
    }
    return moves.contains(to)
  }

  def movePiece(from: Position, to: Position): Field = {
    val piece = pieces.get(from)

    piece match {
      case Some(p) => {
        val newPieces = pieces - from + (to -> p)
        return Field(newPieces)
      }
      case None => {
        return this
      }
    }
  }

  def getPiece(x: Int, y: Int): Option[Piece] = {
    return pieces.get(Position(x, y))
  }

  def printField() = {
    println("  a b c d e f g h")
    println("  ---------------")

    for (y <- 8 to 1 by -1) {
      print(y + "|")
      for (x <- 1 to 8 by 1) {
        pieces.get(Position(x, y)) match {
          case Some(piece) => print(piece.getSymbol() + " ")
          case None        => print("  ")
        }
      }
      println("|" + y)
    }

    println("  ---------------")
    println("  a b c d e f g h")
  }
}
