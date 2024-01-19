package model.field

import scala.collection.immutable.Map

import model.position.PositionBaseImpl
import model.field.FieldInterface
import model.position.PositionInterface
import model.pieces.King
import util.color.*
import com.google.inject.Inject
import model.pieces.Piece

// FieldBaseImpl class represents the chessboard state, including piece positions and the current player.
case class FieldBaseImpl(
    pieces: Map[PositionInterface, Piece],
    currentPlayer: Color = Color.White
) extends FieldInterface {
  def getPiece(position: PositionInterface): Option[Piece] =
    pieces.get(position)

  // Checks if the current player is in checkmate.
  def isCheckMate(): Option[Color] =
    // If the current player is in check, evaluate if there are any valid moves left.
    if (isCheck(currentPlayer)) {
      val noValidMoves = pieces
        .filter { case (_, piece) => piece.color == currentPlayer }
        .forall { case (position, piece) =>
          // Check if every move still results in check.
          piece
            .availableMoves(position, this)
            .forall(move =>
              setPiece(move, piece).removePiece(position).isCheck(currentPlayer)
            )
        }
      if (noValidMoves) Some(currentPlayer) else None
    } else None

  // Returns the piece at a specific x, y coordinate.
  def getPiece(x: Int, y: Int): Option[Piece] =
    pieces.get(PositionBaseImpl(x, y))

  // Updates the board by placing a piece at a given position.
  def setPiece(position: PositionInterface, piece: Piece): FieldInterface =
    FieldBaseImpl(pieces + (position -> piece), currentPlayer)

  // Removes a piece from a specific position.
  def removePiece(position: PositionInterface): FieldInterface =
    FieldBaseImpl(pieces - position, currentPlayer)

  // Flips the board (useful for perspective switching).
  def flipBoard(): FieldBaseImpl =
    FieldBaseImpl(pieces.map { case (position, piece) =>
      position.flipPosition() -> piece
    })

  // Returns all pieces on the board.
  def getPieces(): Map[PositionInterface, Piece] = pieces

  // Checks if the specified color is in check.
  def isCheck(color: Color): Boolean =
    findKing(color).exists { kingPosition =>
      pieces.exists { case (position, piece) =>
        piece.color != color && piece
          .availableMoves(position, this)
          .contains(kingPosition)
      }
    }

  // Finds the king of a specific color.
  def findKing(color: Color): Option[PositionInterface] =
    pieces.collectFirst {
      case (position, piece: King) if piece.color == color => position
    }

  // Returns the current player.
  def getCurrentPlayer(): Color = currentPlayer

  // Flips the current player.
  def flipPlayer(): FieldBaseImpl =
    FieldBaseImpl(pieces, flipColor(currentPlayer))

  // Flips the color
  def flipColor(color: Color): Color =
    if (color == Color.White) Color.Black else Color.White

  // Checks if a position is within the valid range of the board.
  def isPositionValid(pos: PositionInterface): Boolean =
    pos.getX() >= 1 && pos.getX() <= 8 && pos.getY() >= 1 && pos.getY() <= 8
}
