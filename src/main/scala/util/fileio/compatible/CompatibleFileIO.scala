package util.fileio.compatible

import util.fileio.FileIO
import model.gamestate.GameStateInterface
import util.fileio.xml.XMLFileIO
import util.fileio.json.JsonFileIO

final class CompatibleFileIO extends FileIO {
  def getFileExtension(path: String): String = {
    path.split("\\.").last
  }

  def fromData(data: String, path: String): GameStateInterface = {
    getFileExtension(path) match {
      case "xml"  => new XMLFileIO().fromData(data, path)
      case "json" => new JsonFileIO().fromData(data, path)
      case _      => null
    }
  }

  def toData(gameState: GameStateInterface, path: String): String = {
    getFileExtension(path) match {
      case "xml"  => new XMLFileIO().toData(gameState, path)
      case "json" => new JsonFileIO().toData(gameState, path)
      case _      => null
    }
  }
}
