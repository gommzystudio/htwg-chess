package model.commands

import model.Field
import scala.util.Try

abstract class Command() {
  def execute(): Try[Field]
  def undo(): Try[Field]
}
