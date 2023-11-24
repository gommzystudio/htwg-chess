package model

import scala.collection.immutable.Map

import model.pieces.*
import model.Position

case class Field(pieces: Map[Position, Piece]) {
  def getPiece(position: Position): Option[Piece] = {
    return pieces.get(position)
  }

  def getPiece(x: Int, y: Int): Option[Piece] = {
    return pieces.get(Position(x, y))
  }

  def setPiece(position: Position, piece: Piece): Field = {
    return Field(pieces + (position -> piece))
  }

  def removePiece(position: Position): Field = {
    return Field(pieces - position)
  }

  def flipBoard(): Field = {
    var newPieces = Map[Position, Piece]()
    for ((position, piece) <- pieces) {
      newPieces = newPieces + (position.flipPosition() -> piece)
    }
    return Field(newPieces)
  }
}
