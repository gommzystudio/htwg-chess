import com.google.inject.{AbstractModule, TypeLiteral}
import controller.{ControllerBaseImpl, ControllerInterface}
import model.commands.Command
import model.field.{FieldBaseImpl, FieldFactory, FieldInterface}
import model.gamestate.{GameStateBaseImpl, GameStateInterface}
import model.pieces.Piece
import model.position.{PositionBaseImpl, PositionInterface}
import util.color.Color
import util.fileio.FileIO
import util.fileio.json.JsonFileIO
import util.updater.{UpdaterBaseImpl, UpdaterInterface}

import java.util.ResourceBundle.Control
import scala.collection.immutable.Map

final case class SchachMattModule() extends AbstractModule {
  override def configure() = {
    bind(classOf[GameStateInterface]).toInstance(
      new GameStateBaseImpl(FieldFactory.createCheckMateExample().flipPlayer())
    )
    bind(classOf[FileIO]).toInstance(
      JsonFileIO()
    )
    bind(classOf[ControllerInterface]).to(classOf[ControllerBaseImpl])
  }
}
