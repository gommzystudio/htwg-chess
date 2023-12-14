package model.commands

import model.field.FieldInterface
import scala.util.Try

trait Command {
  def execute(): Try[FieldInterface]
  def undo(): Try[FieldInterface]
}
