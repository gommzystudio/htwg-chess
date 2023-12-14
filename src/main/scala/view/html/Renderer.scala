package view.html

import model.gamestate.GameStateInterface
import model.position.PositionInterface
import model.position.PositionBaseImpl

final case class Renderer() {
  def defaultStructure(): String = {
    return """|<!DOCTYPE html>
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
           |</script>
           |</head>
           |<body> 
           |</body>
           |</html>
           |""".stripMargin
  }

  def render(
      gameState: GameStateInterface,
      availableMoves: List[PositionInterface] = List()
  ): String = {
    return """
<div class="bg-gray-100 w-screen h-screen">
  <div class="h-screen w-screen bg-gray-300">
    <div class="flex flex-col gap-3 p-5">
      <div class="w-full rounded-3xl bg-gray-50 shadow-lg text-center justify-center text-gray-400 font-bold">
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

        <div class="flex w-full justify-between">
          <div class="w-8 m-auto">8</div>
          <div class="grid grid-cols-8 w-full rounded-t-xl overflow-hidden shadow-lg">
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      1,
      8
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      2,
      8
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      3,
      8
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      4,
      8
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      5,
      8
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      6,
      8
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      7,
      8
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      8,
      8
    ) + """</div>
          </div>
          <div class="w-8 m-auto">8</div>
        </div>

        <div class="flex w-full justify-between">
          <div class="w-8 m-auto">7</div>
          <div class="grid grid-cols-8 w-full shadow-lg">
            <div class="bg-gray-900 square" onclick="selectField('a7')">""" + renderPiece(
      gameState,
      availableMoves,
      1,
      7
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      2,
      7
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      3,
      7
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      4,
      7
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      5,
      7
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      6,
      7
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      7,
      7
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      8,
      7
    ) + """</div>
          </div>
          <div class="w-8 m-auto">7</div>
        </div>

        <div class="flex w-full justify-between">
          <div class="w-8 m-auto">6</div>
          <div class="grid grid-cols-8 w-full shadow-lg">
            <div class="bg-gray-200 square"">""" + renderPiece(
      gameState,
      availableMoves,
      1,
      6
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      2,
      6
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      3,
      6
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      4,
      6
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      5,
      6
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      6,
      6
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      7,
      6
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      8,
      6
    ) + """</div>
          </div>
          <div class="w-8 m-auto">6</div>
        </div>

        <div class="flex w-full justify-between">
          <div class="w-8 m-auto">5</div>
          <div class="grid grid-cols-8 w-full shadow-lg">
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      1,
      5
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      2,
      5
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      3,
      5
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      4,
      5
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      5,
      5
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      6,
      5
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      7,
      5
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      8,
      5
    ) + """</div>
          </div>
          <div class="w-8 m-auto">5</div>
        </div>

        <div class="flex w-full justify-between">
          <div class="w-8 m-auto">4</div>
          <div class="grid grid-cols-8 w-full shadow-lg">
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      1,
      4
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      2,
      4
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      3,
      4
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      4,
      4
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      5,
      4
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      6,
      4
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      7,
      4
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      8,
      4
    ) + """</div>
          </div>
          <div class="w-8 m-auto">4</div>
        </div>

        <div class="flex w-full justify-between">
          <div class="w-8 m-auto">3</div>
          <div class="grid grid-cols-8 w-full shadow-lg">
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      1,
      3
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      2,
      3
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      3,
      3
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      4,
      3
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      5,
      3
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      6,
      3
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      7,
      3
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      8,
      3
    ) + """</div>
          </div>
          <div class="w-8 m-auto">3</div>
        </div>

        <div class="flex w-full justify-between">
          <div class="w-8 m-auto">2</div>
          <div class="grid grid-cols-8 w-full shadow-lg">
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      1,
      2
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      2,
      2
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      3,
      2
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      4,
      2
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      5,
      2
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      6,
      2
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      7,
      2
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      8,
      2
    ) + """</div>
          </div>
          <div class="w-8 m-auto">2</div>
        </div>

        <div class="flex w-full justify-between">
          <div class="w-8 m-auto">1</div>
          <div class="grid grid-cols-8 w-full rounded-b-xl overflow-hidden shadow-lg">
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      1,
      1
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      2,
      1
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      3,
      1
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      4,
      1
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      5,
      1
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      6,
      1
    ) + """</div>
            <div class="bg-gray-900 square">""" + renderPiece(
      gameState,
      availableMoves,
      7,
      1
    ) + """</div>
            <div class="bg-gray-200 square">""" + renderPiece(
      gameState,
      availableMoves,
      8,
      1
    ) + """</div>
          </div>        
          <div class="w-8 m-auto">1</div>
        </div>

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
      </div>

      <div class="flex p-5 gap-5 w-full justify-center rounded-3xl bg-gray-50 text-center font-bold text-gray-400 shadow-lg">
        <button onclick="undo()" class="w-1/2 bg-gray-200 rounded-xl shadow-lg p-3 font-bold">Rückgängig</button>
        <button onclick="redo()" class="w-1/2 bg-gray-200 rounded-xl shadow-lg p-3 font-bold">Wiederherstellen</button>
      </div>    
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
}
