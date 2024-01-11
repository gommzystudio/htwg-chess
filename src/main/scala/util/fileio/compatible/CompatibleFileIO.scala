package util.fileio.compatible

import util.fileio.xml.XMLFileIO
import util.fileio.json.JsonFileIO
import util.fileio.FileIO
import model.gamestate.GameStateInterface

final case class CompatibleFileIO(path: String) extends FileIO(path) {
  def getFileExtension(): String = {
    return path.split('.').last
  }

  def fromData(data: String): GameStateInterface = {
    getFileExtension() match {
      case "json" => JsonFileIO(path).fromData(data)
      case "xml"  => XMLFileIO(path).fromData(data)
    }
  }

  def toData(gameState: GameStateInterface): String = {
    getFileExtension() match {
      case "json" => JsonFileIO(path).toData(gameState)
      case "xml"  => XMLFileIO(path).toData(gameState)
    }
  }
}
