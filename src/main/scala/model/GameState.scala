package model

class GameState(field: Field) {
  def getField(): Field = {
    return field;
  }

  def updateField(field: Field): GameState = {
    return GameState(field);
  }

  def movePiece(from: Position, to: Position): GameState = {
    return updateField(field.movePiece(from, to));
  }
}
