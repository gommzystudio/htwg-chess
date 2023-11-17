package model

import scala.collection.immutable.Map

import model.pieces.*
import model.Position

case class Field(
    pieces: Map[Position, Piece] = Map(
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
) {

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

  // flipBoard means that every piece is mirrored on the y-axis
  def flipBoard(): Field = {
    var newPieces = Map[Position, Piece]()
    for ((position, piece) <- pieces) {
      newPieces = newPieces + (position.flipPosition() -> piece)
    }
    return Field(newPieces)
  }
}
