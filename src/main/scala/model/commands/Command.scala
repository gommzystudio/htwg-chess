package model.commands

import model.GameState

abstract class Command() {
  def execute(): GameState
  def undo(): GameState
  def redo(): GameState
}
