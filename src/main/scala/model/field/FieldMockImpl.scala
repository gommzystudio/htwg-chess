package model.field

import scala.collection.immutable.Map

import model.pieces.*
import model.position.PositionInterface
import model.field.FieldInterface

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

  def getPieces(): List[Piece] = {
    return List()
  }
}
