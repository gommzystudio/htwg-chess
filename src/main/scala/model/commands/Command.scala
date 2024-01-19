package model.commands

import model.field.FieldInterface
import scala.util.Try

// Represents a command that can be executed, undone, and redone (redone is just a new execution).
trait Command {
  def execute(): Try[FieldInterface]
  def undo(): Try[FieldInterface]
}
