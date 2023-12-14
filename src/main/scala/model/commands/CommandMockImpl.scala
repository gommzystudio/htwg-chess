package model.commands

import model.field.FieldInterface
import scala.util.Try
import model.field.FieldMockImpl
import model.commands.Command

case class CommandMockImpl() extends Command {
  def execute(): Try[FieldInterface] = Try(new FieldMockImpl())
  def undo(): Try[FieldInterface] = Try(new FieldMockImpl())
}
