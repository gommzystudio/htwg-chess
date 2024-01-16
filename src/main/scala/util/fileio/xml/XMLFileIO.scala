package util.fileio.xml

import com.google.inject.Inject
import util.fileio.FileIO
import model.gamestate.GameStateInterface
import play.api.libs.json.Json
import scala.xml.{Elem, Node, XML}
import model.position.PositionBaseImpl
import model.pieces.PieceFactory
import model.gamestate.GameStateBaseImpl
import model.field.FieldBaseImpl

final class XMLFileIO extends FileIO() {
  override def fromData(xmlString: String, path: String): GameStateInterface = {
    val xml: Elem = XML.loadString(xmlString)

    val currentPlayer = (xml \ "currentPlayer").text
    val pieces = (xml \ "pieces" \ "piece").map { pieceNode =>
      val x = (pieceNode \ "position" \ "x").text.toInt
      val y = (pieceNode \ "position" \ "y").text.toInt
      val color = (pieceNode \ "color").text match {
        case "White" => util.color.Color.White
        case "Black" => util.color.Color.Black
      }
      val pieceType = (pieceNode \ "class").text

      val position = PositionBaseImpl(x, y)
      val piece = PieceFactory.createPiece(pieceType, color)

      (position, piece)
    }

    new GameStateBaseImpl(
      new FieldBaseImpl(
        pieces.toMap,
        currentPlayer match {
          case "White" => util.color.Color.White
          case "Black" => util.color.Color.Black
        }
      )
    )
  }

  override def toData(gameState: GameStateInterface, path: String): String = {
    val piecesXml =
      gameState.getField().getPieces().map { case (position, piece) =>
        <piece>
  <position>
    <x>{position.getX()}</x>
    <y>{position.getY()}</y>
  </position>
  <color>{piece.color.toString()}</color>
  <class>{piece.getClass().getSimpleName()}</class>
</piece>
      }

    val data: Elem =
      <gameState>
  <currentPlayer>{
        gameState.getField().getCurrentPlayer().toString()
      }</currentPlayer>
  <pieces>{piecesXml}</pieces>
</gameState>

    data.toString()
  }
}
