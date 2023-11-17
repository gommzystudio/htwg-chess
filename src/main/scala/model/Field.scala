package model

import scala.collection.immutable.Map

import model.pieces.*
import model.Position

case class Field(pieces: Map[Position, Piece]) {
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

  def getPiece(position: Position): Option[Piece] = {
    return pieces.get(position)
  }

  def getPiece(x: Int, y: Int): Option[Piece] = {
    return pieces.get(Position(x, y))
  }

  // flipBoard means that every piece is mirrored on the x/y axis
  def flipBoard(): Field = {
    var newPieces = Map[Position, Piece]()
    for ((position, piece) <- pieces) {
      newPieces = newPieces + (position.flipPosition() -> piece)
    }
    return Field(newPieces)
  }
}
