import com.google.inject.AbstractModule
import controller.ControllerInterface
import controller.ControllerBaseImpl
import model.gamestate.GameStateBaseImpl
import model.field.FieldInterface
import model.gamestate.GameStateInterface
import model.field.FieldBaseImpl
import util.updater.UpdaterInterface
import util.updater.UpdaterBaseImpl
import model.position.PositionInterface
import model.position.PositionBaseImpl
import model.commands.CommandMockImpl
import model.commands.Command
import model.field.FieldFactory
import com.google.inject.TypeLiteral
import model.pieces.Piece
import scala.collection.immutable.Map
import util.color.Color
import util.fileio.FileIO
import util.fileio.json.JsonFileIO

final case class SchachJsonModule() extends AbstractModule {
  override def configure() = {
    bind(classOf[GameStateInterface]).toInstance(
      new GameStateBaseImpl(FieldFactory.createInitialField())
    )
    bind(classOf[FileIO]).toInstance(
      JsonFileIO()
    )
    bind(classOf[ControllerInterface]).to(classOf[ControllerBaseImpl])
  }
}
