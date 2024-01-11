package util.fileio

import model.gamestate.GameStateInterface

trait FileIO(path: String) {
  def read(): GameStateInterface = {
    import scala.io.Source

    if (!new java.io.File(path).exists) {
      return null
    }

    val bufferedSource = Source.fromFile(path)
    val data = bufferedSource.getLines.mkString
    bufferedSource.close
    return fromData(data)
  }

  def fromData(data: String): GameStateInterface

  def write(gameState: GameStateInterface): Unit = {
    import java.io._

    // create folder if not exists
    val folder = new File(path.split('/').dropRight(1).mkString("/"))
    if (!folder.exists()) {
      folder.mkdirs()
    }

    val pw = new PrintWriter(new File(path))
    pw.write(toData(gameState))
    pw.close()
  }

  def toData(gameState: GameStateInterface): String
}
