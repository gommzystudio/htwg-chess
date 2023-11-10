package model.pieces

import scala.annotation.switch
import scala.collection.mutable.ArrayBuffer

import model.Position
import model.Field

class Pawn(c: Color) extends Piece(c) {
  override def getSymbol() = {
    return if (c == Color.White) "♙" else "♟"
  }

  override def availableMoves(
      position: Position,
      field: Field
  ): List[Position] = {
    if (c == Color.White) {
      var moves = List[Position]()

      // Standard move
      if (field.getPiece(position.x, position.y + 1) == None) {
        moves = Position(position.x, position.y + 1) :: moves
      }

      // First move
      if (position.y == 2) {
        if (field.getPiece(position.x, position.y + 2) == None) {
          moves = Position(position.x, position.y + 2) :: moves
        }
      }

      // Capture
      if (field.getPiece(position.x + 1, position.y + 1) != None) {
        moves = Position(position.x + 1, position.y + 1) :: moves
      }

      if (field.getPiece(position.x - 1, position.y + 1) != None) {
        moves = Position(position.x - 1, position.y + 1) :: moves
      }

      return moves
    } else {
      var moves = List[Position]()

      // Standard move
      if (field.getPiece(position.x, position.y - 1) == None) {
        moves = Position(position.x, position.y - 1) :: moves
      }

      // First move
      if (position.y == 7) {
        if (field.getPiece(position.x, position.y - 2) == None) {
          moves = Position(position.x, position.y - 2) :: moves
        }
      }

      // Capture
      if (field.getPiece(position.x + 1, position.y - 1) != None) {
        moves = Position(position.x + 1, position.y - 1) :: moves
      }

      if (field.getPiece(position.x - 1, position.y - 1) != None) {
        moves = Position(position.x - 1, position.y - 1) :: moves
      }

      return moves
    }
  }
}
