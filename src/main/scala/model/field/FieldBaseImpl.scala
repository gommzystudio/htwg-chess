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
  def getPiece(position: PositionInterface): Option[Piece] =
    pieces.get(position)

  def isCheckMate(): Option[Color] =
    if (isCheck(currentPlayer)) {
      val noValidMoves = pieces
        .filter { case (_, piece) => piece.color == currentPlayer }
        .forall { case (position, piece) =>
          piece
            .availableMoves(position, this)
            .forall(move =>
              setPiece(move, piece).removePiece(position).isCheck(currentPlayer)
            )
        }
      if (noValidMoves) Some(currentPlayer) else None
    } else None

  def getPiece(x: Int, y: Int): Option[Piece] =
    pieces.get(PositionBaseImpl(x, y))

  def setPiece(position: PositionInterface, piece: Piece): FieldInterface =
    FieldBaseImpl(pieces + (position -> piece), currentPlayer)

  def removePiece(position: PositionInterface): FieldInterface =
    FieldBaseImpl(pieces - position, currentPlayer)

  def flipBoard(): FieldBaseImpl =
    FieldBaseImpl(pieces.map { case (position, piece) =>
      position
        .flipPosition() -> piece
    })

  def getPieces(): Map[PositionInterface, Piece] = pieces

  def isCheck(color: Color): Boolean =
    findKing(color).exists { kingPosition =>
      pieces.exists { case (position, piece) =>
        piece.color != color && piece
          .availableMoves(position, this)
          .contains(kingPosition)
      }
    }

  def findKing(color: Color): Option[PositionInterface] =
    pieces.collectFirst {
      case (position, piece: King) if piece.color == color => position
    }

  def getCurrentPlayer(): Color = currentPlayer

  def flipPlayer(): FieldBaseImpl =
    FieldBaseImpl(pieces, flipColor(currentPlayer))

  def flipColor(color: Color): Color =
    if (color == Color.White) Color.Black else Color.White

  def isPositionValid(pos: PositionInterface): Boolean =
    pos.getX() >= 1 && pos.getX() <= 8 && pos.getY() >= 1 && pos.getY() <= 8
}
