package model.field

import scala.collection.immutable.Map

import model.position.PositionBaseImpl
import model.field.FieldInterface
import model.position.PositionInterface
import model.pieces.King
import util.color.*
import com.google.inject.Inject
import model.pieces.Piece

case class FieldBaseImpl(
    pieces: Map[PositionInterface, Piece],
    currentPlayer: Color = Color.White
) extends FieldInterface {
  def getPiece(position: PositionInterface): Option[Piece] = {
    return pieces.get(position)
  }

  def getPiece(x: Int, y: Int): Option[Piece] = {
    return pieces.get(PositionBaseImpl(x, y))
  }

  def setPiece(position: PositionInterface, piece: Piece): FieldInterface = {
    return FieldBaseImpl(pieces + (position -> piece), currentPlayer)
  }

  def removePiece(position: PositionInterface): FieldInterface = {
    return FieldBaseImpl(pieces - position, currentPlayer)
  }

  def flipBoard(): FieldBaseImpl = {
    var newPieces = Map[PositionInterface, Piece]()
    for ((position, piece) <- pieces) {
      newPieces = newPieces + (position.flipPosition() -> piece)
    }
    return FieldBaseImpl(newPieces)
  }

  def getPieces(): Map[PositionInterface, Piece] = {
    return pieces
  }

  def isCheck(color: Color): Boolean = {
    var kingPosition: PositionInterface = PositionBaseImpl(0, 0)
    for ((position, piece) <- pieces) {
      if (piece.isInstanceOf[King] && piece.color == color) {
        kingPosition = position
      }
    }

    for ((position, piece) <- pieces) {
      if (piece.color != color) {
        val validMoves = piece.availableMoves(position, this)
        if (validMoves.contains(kingPosition)) {
          return true
        }
      }
    }

    return false
  }

  def getCurrentPlayer(): Color = {
    return currentPlayer
  }

  def flipPlayer(): FieldBaseImpl = {
    return FieldBaseImpl(pieces, flipColor(currentPlayer))
  }
}
