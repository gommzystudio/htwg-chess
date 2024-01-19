package view.html

import model.gamestate.GameStateInterface
import model.position.PositionInterface
import model.position.PositionBaseImpl

// Renderer class to render the HTML content for the web view.
final case class Renderer() {
  // Returns the default HTML structure for the web view.
  def defaultStructure(): String = {
    s"""|<!DOCTYPE html>
           |<html lang="en">
           |<head>
           |    <meta charset="UTF-8">
           |    <meta name="viewport" content="width=device-width, initial-scale=1.0">
           |    <title>Chess</title>
           |    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@latest/dist/tailwind.min.css" rel="stylesheet">  
           |    <script>
           |  var startField = null;
           |
           |  function sendData(command, args) {
           |    alert(command + ';' + args.join(';'));
           |  }
           |
           |  function startDrag(from) {
           |    startField = from;
           |    sendData('loadMoves', [from]);
           |  }
           |
           |  function selectField(to) {
           |    sendData('move', [startField, to]);
           |  }
           |
           |  function undo() {
           |    sendData('undo', []);
           |  }
           |
           |  function redo() {
           |    sendData('redo', []);
           |  }
           |
           |  function save() {
           |    const path = document.getElementById('path').value;
           |    sendData('save', [path]);
           |  }
           |
           |  function load() {
           |    const path = document.getElementById('path').value;
           |    sendData('load', [path]);
           |  }
           |</script>
           |</head>
           |<body> 
           |</body>
           |</html>
           |""".stripMargin
  }

  // Creates the game board.
  def render(
      gameState: GameStateInterface,
      availableMoves: List[PositionInterface] = List(),
      winner: String
  ): String = {
    s"""
<div class="bg-gray-100 w-screen h-screen">
  <div class="h-screen w-screen bg-gray-300">
    <div class="flex flex-col gap-3 p-5">
      <div class="w-full rounded-3xl bg-gray-50 shadow-lg text-center justify-center text-gray-400 font-bold relative overflow-hidden">
        ${showWinnerOverlay(winner)}

        ${renderBoardBorder()}

        ${renderBoardRows(gameState, availableMoves)}

        ${renderBoardBorder()}
      </div>

      ${renderControls()}
    
    </div>
  </div>
</div>

<style>
.square::before {
  content: '';
  display: block;
  padding-top: 100%;  /* Aspect Ratio 1:1 */
}

.square {
  display: flex;
  justify-content: center;
  position: relative;
  align-items: center;
  font-size: 3rem; /* Größe der Schachfigur */
}
</style>

"""
  }

  // Creates the controls for the game.
  def renderControls(): String = {
    s"""
    <div class="flex w-full justify-center gap-5 rounded-3xl bg-gray-50 p-5 text-center font-bold text-gray-400 shadow-lg">
      <button onclick="undo()" class="w-full rounded-xl bg-gray-200 p-3 font-bold shadow-lg">Rückgängig</button>
      <button onclick="redo()" class="w-full rounded-xl bg-gray-200 p-3 font-bold shadow-lg">Wiederherstellen</button>

      <input type="text" id="path" placeholder="Datei" class="w-40 rounded-xl bg-gray-200 px-3" />
      <button onclick="save()" class="w-full rounded-xl bg-gray-200 p-3 font-bold shadow-lg">Speichern</button>
      <button onclick="load()" class="w-full rounded-xl bg-gray-200 p-3 font-bold shadow-lg">Laden</button>
    </div>
    """
  }

  // Creates the border at the top and bottom of the board.
  def renderBoardBorder(): String = {
    s"""
        <div class="flex w-full h-8">
          <div class="w-8"></div>
          <div class="flex w-full justify-around h-10 items-center text-center">
            <div>A</div>
            <div>B</div>
            <div>C</div>
            <div>D</div>
            <div>E</div>
            <div>F</div>
            <div>G</div>
            <div>H</div>
          </div>
          <div class="w-8"></div>
        </div>
        """
  }

  // Creates the rows of the board.
  def renderBoardRows(
      gameState: GameStateInterface,
      availableMoves: List[PositionInterface]
  ): String = {
    return (8 to 1 by -1)
      .map { row =>
        renderBoardRow(gameState, availableMoves, row)
      }
      .mkString("")
  }

  // Creates a row of the board.
  def renderBoardRow(
      gameState: GameStateInterface,
      availableMoves: List[PositionInterface],
      row: Int
  ): String =
    s"""
        <div class="flex w-full justify-between">
          <div class="w-8 m-auto">${row}</div>
          <div class="grid grid-cols-8 w-full overflow-hidden shadow-lg ${getBoardRowRounding(
        row
      )}">
            ${(1 to 8)
        .map { x =>
          """
                <div class="""" + getColor(
            x,
            row
          ) + """ square">""" + renderPiece(
            gameState,
            availableMoves,
            x,
            row
          ) + """</div>
            """
        }
        .mkString("")}
          </div>
          <div class="w-8 m-auto">${row}</div>
        </div>
        """

  // Rounds the top and bottom of the board.
  def getBoardRowRounding(row: Int): String = {
    if (row == 8) {
      return "rounded-t-xl"
    } else if (row == 1) {
      return "rounded-b-xl"
    } else {
      return ""
    }
  }

  // Every second field is grayed out.
  def getColor(row: Int, col: Int): String = {
    if ((row + col) % 2 == 1) {
      return "bg-gray-200"
    } else {
      return "bg-gray-700"
    }
  }

  // Creates a piece on the board.
  def renderPiece(
      gameState: GameStateInterface,
      availableMoves: List[PositionInterface],
      x: Int,
      y: Int
  ): String = {
    val piece = gameState.getField().getPiece(PositionBaseImpl(x, y))
    piece match {
      case Some(piece) =>
        return """<div class="cursor-pointer" onclick="startDrag('""" + x + y + """')">""" + piece
          .getSymbol() + """</div>""" + renderPossibleMoves(
          availableMoves,
          x,
          y
        )
      case None =>
        return renderPossibleMoves(availableMoves, x, y)
    }
  }

  // Creates a possible move on the board.
  def renderPossibleMoves(
      availableMoves: List[PositionInterface],
      x: Int,
      y: Int
  ): String = {
    if (availableMoves.contains(PositionBaseImpl(x, y))) {
      return """
    <div class="absolute z-0 h-5/6 w-5/6 rounded-full bg-blue-500 opacity-50 cursor-pointer" onclick="selectField('""" + x + y + """')"></div>
    """
    } else {
      return ""
    }
  }

  // Shows the winner overlay if the game is over.
  def showWinnerOverlay(winner: String): String = {
    winner match {
      case "White" =>
        return """
        <div class="z-10 absolute flex h-full w-full items-center bg-white bg-opacity-50">
          <h1 class="text-2xl w-full text-center">Weiß hat gewonnen!</h1>
        </div>
        """
      case "Black" =>
        return """
        <div class="z-10 absolute flex h-full w-full items-center bg-white bg-opacity-50">
          <h1 class="text-2xl w-full text-center">Schwarz hat gewonnen!</h1>
        </div>
        """
      case _ =>
        return ""
    }
  }
}
