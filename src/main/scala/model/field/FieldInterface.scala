package model.field

import model.pieces.Piece
import model.position.PositionInterface
import util.color.Color

trait FieldInterface {
  def getPieces(): List[Piece]
  def getPiece(position: PositionInterface): Option[Piece]
  def getPiece(x: Int, y: Int): Option[Piece]
  def setPiece(position: PositionInterface, piece: Piece): FieldInterface
  def removePiece(position: PositionInterface): FieldInterface
  def flipBoard(): FieldInterface
  def isCheck(color: Color): Boolean
  def getCurrentPlayer(): Color
  def flipPlayer(): FieldInterface
}
