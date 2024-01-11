package model.pieces

import util.color.Color

object PieceFactory {
  def createPiece(pieceType: String, color: Color): Piece = {
    pieceType match {
      case "Bishop" => Bishop(color)
      case "King"   => King(color)
      case "Knight" => Knight(color)
      case "Pawn"   => Pawn(color)
      case "Queen"  => Queen(color)
      case "Rook"   => Rook(color)
    }
  }
}
