package util.fileio

import model.gamestate.GameStateBaseImpl
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.fileio.json.JsonFileIO
import util.fileio.xml.XMLFileIO
import util.fileio.compatible.CompatibleFileIO

// Only such a rough test is possible, because reading and writing files is required.
// Its still better than nothing.

class FileIOTest extends AnyWordSpec with Matchers with MockitoSugar {
  val gameState = new GameStateBaseImpl()
  val jsonFileIO = new JsonFileIO()
  val xmlFileIO = new XMLFileIO()
  val compatibleFileIO = new CompatibleFileIO()

  val testFolder = "testdata/"

  "JsonFileIO" should {
    "save and load a game state" in {
      val path = testFolder + "jsonFileIO.json"
      jsonFileIO.write(path, gameState)
      val loadedGameState = jsonFileIO.read(path)
      loadedGameState.getField().getPieces().size should be(
        gameState.getField().getPieces().size
      )
    }
  }

  "XMLFileIO" should {
    "save and load a game state" in {
      val path = testFolder + "xmlFileIO.xml"
      xmlFileIO.write(path, gameState)
      val loadedGameState = xmlFileIO.read(path)
      loadedGameState.getField().getPieces().size should be(
        gameState.getField().getPieces().size
      )
    }
  }

  "CompatibleFileIO" should {
    "save and load a game state as XML" in {
      val path = testFolder + "compatibleFileIO.xml"
      compatibleFileIO.write(path, gameState)
      val loadedGameState = compatibleFileIO.read(path)
      loadedGameState.getField().getPieces().size should be(
        gameState.getField().getPieces().size
      )
    }
    "save and load a game state as JSON" in {
      val path = testFolder + "compatibleFileIO.json"
      compatibleFileIO.write(path, gameState)
      val loadedGameState = compatibleFileIO.read(path)
      loadedGameState.getField().getPieces().size should be(
        gameState.getField().getPieces().size
      )
    }
  }
}
