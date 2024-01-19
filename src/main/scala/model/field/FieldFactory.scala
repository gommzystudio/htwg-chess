package model.field

import scala.collection.immutable.Map

import model.pieces.*
import model.field.FieldBaseImpl

import model.position.PositionBaseImpl
import util.color.Color

// Factory class to create fields for specific use cases.
object FieldFactory {
  // Creates a field for the end game example.
  def createEndGameExample(): FieldBaseImpl = {
    return FieldBaseImpl(
      Map(
        PositionBaseImpl(1, 1) -> King(Color.Black),
        PositionBaseImpl(1, 8) -> King(Color.White),
        PositionBaseImpl(2, 8) -> Rook(Color.Black)
      )
    );
  }

  // Creates a field for the check mate example.
  def createCheckMateExample(): FieldBaseImpl = {
    return FieldBaseImpl(
      Map(
        PositionBaseImpl(1, 1) -> King(Color.Black),
        PositionBaseImpl(1, 8) -> King(Color.White),
        PositionBaseImpl(2, 8) -> Rook(Color.Black),
        PositionBaseImpl(2, 7) -> Rook(Color.Black)
      )
    );
  }

  // Creates the initial field for a new game.
  def createInitialField(): FieldBaseImpl = {
    return FieldBaseImpl(
      Map(
        PositionBaseImpl(1, 1) -> Rook(Color.White),
        PositionBaseImpl(2, 1) -> Knight(Color.White),
        PositionBaseImpl(3, 1) -> Bishop(Color.White),
        PositionBaseImpl(4, 1) -> Queen(Color.White),
        PositionBaseImpl(5, 1) -> King(Color.White),
        PositionBaseImpl(6, 1) -> Bishop(Color.White),
        PositionBaseImpl(7, 1) -> Knight(Color.White),
        PositionBaseImpl(8, 1) -> Rook(Color.White),
        PositionBaseImpl(1, 2) -> Pawn(Color.White),
        PositionBaseImpl(2, 2) -> Pawn(Color.White),
        PositionBaseImpl(3, 2) -> Pawn(Color.White),
        PositionBaseImpl(4, 2) -> Pawn(Color.White),
        PositionBaseImpl(5, 2) -> Pawn(Color.White),
        PositionBaseImpl(6, 2) -> Pawn(Color.White),
        PositionBaseImpl(7, 2) -> Pawn(Color.White),
        PositionBaseImpl(8, 2) -> Pawn(Color.White),
        PositionBaseImpl(1, 8) -> Rook(Color.Black),
        PositionBaseImpl(2, 8) -> Knight(Color.Black),
        PositionBaseImpl(3, 8) -> Bishop(Color.Black),
        PositionBaseImpl(4, 8) -> Queen(Color.Black),
        PositionBaseImpl(5, 8) -> King(Color.Black),
        PositionBaseImpl(6, 8) -> Bishop(Color.Black),
        PositionBaseImpl(7, 8) -> Knight(Color.Black),
        PositionBaseImpl(8, 8) -> Rook(Color.Black),
        PositionBaseImpl(1, 7) -> Pawn(Color.Black),
        PositionBaseImpl(2, 7) -> Pawn(Color.Black),
        PositionBaseImpl(3, 7) -> Pawn(Color.Black),
        PositionBaseImpl(4, 7) -> Pawn(Color.Black),
        PositionBaseImpl(5, 7) -> Pawn(Color.Black),
        PositionBaseImpl(6, 7) -> Pawn(Color.Black),
        PositionBaseImpl(7, 7) -> Pawn(Color.Black),
        PositionBaseImpl(8, 7) -> Pawn(Color.Black)
      )
    );
  }
}
