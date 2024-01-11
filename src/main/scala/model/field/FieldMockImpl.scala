package model.field

import scala.collection.immutable.Map

import model.position.PositionInterface
import model.field.FieldInterface
import util.color.Color
import model.pieces.Piece

case class FieldMockImpl() extends FieldInterface {
  def getPiece(position: PositionInterface): Option[Piece] = {
    return None
  }

  def getPiece(x: Int, y: Int): Option[Piece] = {
    return None
  }

  def setPiece(position: PositionInterface, piece: Piece): FieldInterface = {
    return this
  }

  def removePiece(position: PositionInterface): FieldInterface = {
    return this
  }

  def flipBoard(): FieldInterface = {
    return this
  }

  def getPieces(): Map[PositionInterface, Piece] = {
    return Map[PositionInterface, Piece]()
  }

  def isCheck(color: Color): Boolean = {
    return false
  }

  def getCurrentPlayer(): Color = {
    return Color.White
  }

  def flipPlayer(): FieldInterface = {
    return this
  }
}
