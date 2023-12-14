package model.field

import scala.collection.immutable.Map

import model.pieces.*
import model.position.PositionBaseImpl
import model.field.FieldInterface
import model.position.PositionInterface

case class FieldBaseImpl(pieces: Map[PositionInterface, Piece])
    extends FieldInterface {
  def getPiece(position: PositionInterface): Option[Piece] = {
    return pieces.get(position)
  }

  def getPiece(x: Int, y: Int): Option[Piece] = {
    return pieces.get(PositionBaseImpl(x, y))
  }

  def setPiece(position: PositionInterface, piece: Piece): FieldInterface = {
    return FieldBaseImpl(pieces + (position -> piece))
  }

  def removePiece(position: PositionInterface): FieldInterface = {
    return FieldBaseImpl(pieces - position)
  }

  def flipBoard(): FieldBaseImpl = {
    var newPieces = Map[PositionInterface, Piece]()
    for ((position, piece) <- pieces) {
      newPieces = newPieces + (position.flipPosition() -> piece)
    }
    return FieldBaseImpl(newPieces)
  }

  def getPieces(): List[Piece] = {
    return pieces.values.toList
  }
}
