package util.fileio

import model.gamestate.GameStateInterface

trait FileIO() {
  def setSavesDirectory(path: String): String = {
    return "saves/" + path
  }

  def read(path: String): GameStateInterface = {
    import scala.io.Source

    if (!new java.io.File(setSavesDirectory(path)).exists) {
      return null
    }

    val bufferedSource = Source.fromFile(setSavesDirectory(path))
    val data = bufferedSource.getLines.mkString
    bufferedSource.close
    return fromData(data, path)
  }

  def fromData(data: String, path: String): GameStateInterface

  def write(path: String, gameState: GameStateInterface): Unit = {
    import java.io._

    // create folder if not exists
    val folder = new File(
      setSavesDirectory(path).split('/').dropRight(1).mkString("/")
    )
    if (!folder.exists()) {
      folder.mkdirs()
    }

    val pw = new PrintWriter(new File(setSavesDirectory(path)))
    pw.write(toData(gameState, path))
    pw.close()
  }

  def toData(gameState: GameStateInterface, path: String): String
}
