package model.field

import model.pieces.Piece
import model.position.PositionInterface

trait FieldInterface {
  def getPieces(): List[Piece]
  def getPiece(position: PositionInterface): Option[Piece]
  def getPiece(x: Int, y: Int): Option[Piece]
  def setPiece(position: PositionInterface, piece: Piece): FieldInterface
  def removePiece(position: PositionInterface): FieldInterface
  def flipBoard(): FieldInterface
}
