package model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import model.Position
import model.pieces.Piece
import model.pieces.Color
import model.pieces.Pawn
import model.Position

class GameSateSpec extends AnyFlatSpec with Matchers {
  "GameState" should "return the current field" in {
    val field = Field(Map())
    val gameState = new GameState(field)

    gameState.getField() should be theSameInstanceAs field
  }

  it should "update the field" in {
    val field1 = Field(Map())
    val field2 = Field(Map(Position(1, 1) -> Pawn(Color.White)))
    val gameState = new GameState(field1)

    val updatedGameState = gameState.updateField(field2)

    updatedGameState.getField() should be theSameInstanceAs field2
  }
}
