package util.fileio.json

import com.google.inject.Inject
import util.fileio.FileIO
import model.gamestate.GameStateInterface
import play.api.libs.json.Json
import model.field.FieldInterface
import model.position.PositionInterface
import model.commands.Command
import model.gamestate.GameStateBaseImpl
import model.commands.MoveCommand
import play.api.libs.json.JsValue
import model.position.PositionBaseImpl
import model.field.FieldBaseImpl
import model.pieces.*

final case class JsonFileIO @Inject (path: String) extends FileIO(path) {
  override def toData(gameState: GameStateInterface): String = {
    val data = Json.obj(
      "currentPlayer" -> gameState.getField().getCurrentPlayer().toString(),
      "pieces" -> Json.toJson(
        gameState.getField().getPieces().map { case (position, piece) =>
          Json.obj(
            "position" -> Json.obj(
              "x" -> position.getX(),
              "y" -> position.getY()
            ),
            "color" -> piece.color.toString(),
            "class" -> piece.getClass().getSimpleName()
          )
        }
      )
    )
    return Json.prettyPrint(data)
  }

  override def fromData(jsonString: String): GameStateInterface = {
    val fieldJson: JsValue = Json.parse(jsonString)

    val currentPlayer = (fieldJson \ "currentPlayer").as[String]
    val pieces = (fieldJson \ "pieces").as[Seq[JsValue]].map { pieceJson =>
      val x = (pieceJson \ "position" \ "x").as[Int]
      val y = (pieceJson \ "position" \ "y").as[Int]
      val color = (pieceJson \ "color").as[String] match {
        case "White" => util.color.Color.White
        case "Black" => util.color.Color.Black
      }
      val pieceType = (pieceJson \ "class").as[String]

      val position = PositionBaseImpl(x, y)
      val piece = PieceFactory.createPiece(pieceType, color)

      (position, piece)
    }

    new GameStateBaseImpl(
      new FieldBaseImpl(
        pieces.toMap,
        currentPlayer == "White" match {
          case true  => util.color.Color.White
          case false => util.color.Color.Black
        }
      )
    )
  }

}
