package view

import controller.ControllerInterface
import model.position.PositionBaseImpl
import model.gamestate.GameStateInterface
import util.view.ViewInterface
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.web.{WebView, WebEngine}
import javafx.concurrent.Worker.State
import scalafx.application.Platform
import view.html.Renderer
import model.position.PositionInterface
import util.updater.UpdaterInterface
import model.pieces.Piece

class GUI(controller: ControllerInterface)
    extends JFXApp3
    with ViewInterface(controller.asInstanceOf[UpdaterInterface]) {

  private var webEngine: WebEngine = _

  override def start(): Unit = {
    val webView = new WebView()
    setupWebView(webView)
    setupStage(webView)
  }

  private def setupWebView(webView: WebView): Unit = {
    webView.engine.loadContent(loadHtmlContent())
    setupEngineListener(webView.engine)
    webEngine = webView.engine
  }

  private def loadHtmlContent(): String = {
    new Renderer().defaultStructure()
  }

  private def setupEngineListener(engine: WebEngine): Unit = {
    engine.getLoadWorker.stateProperty.addListener((_, _, newState) => {
      if (newState == State.SUCCEEDED) {
        setupAlertHandler(engine)
        controller.addViewAndUpdate(this)
      }
    })
  }

  private def setupAlertHandler(engine: WebEngine): Unit = {
    engine.onAlert = { event => handleAlert(event.getData) }
  }

  private def handleAlert(data: String): Unit = {
    val args = data.split(";")
    val command = args(0)
    args(0) match {
      case "move"      => processMoveCommand(args(1), args(2))
      case "loadMoves" => processLoadMovesCommand(args(1))
      case "undo"      => controller.undoCommand()
      case "redo"      => controller.redoCommand()
      case "save"      => controller.save(args(1))
      case "load"      => controller.load(args(1))
      case _           => println("Unknown command")
    }
  }

  private def processMoveCommand(from: String, to: String): Unit = {
    val fromPos =
      PositionBaseImpl(from.charAt(0).asDigit, from.charAt(1).asDigit)
    val toPos = PositionBaseImpl(to.charAt(0).asDigit, to.charAt(1).asDigit)
    controller.runMoveCommand(fromPos, toPos)
  }

  private def processLoadMovesCommand(position: String): Unit = {
    val pos =
      PositionBaseImpl(position.charAt(0).asDigit, position.charAt(1).asDigit)
    val gameState = controller.getGameState()
    val piece = gameState.getField().getPiece(pos).get
    if (piece.color == gameState.getField().getCurrentPlayer()) {
      val moves = piece.availableMoves(pos, gameState.getField())
      val filteredMoves = filterMovesForCheck(moves, pos, piece, gameState)
      updateGuiWithGameState(gameState, filteredMoves)
    }
  }

  private def filterMovesForCheck(
      moves: List[PositionInterface],
      fromPos: PositionInterface,
      piece: Piece,
      gameState: GameStateInterface
  ): List[PositionInterface] = {
    moves.filter { move =>
      val newField =
        gameState.getField().removePiece(fromPos).setPiece(move, piece)
      !newField.isCheck(piece.color)
    }
  }

  private def setupStage(webView: WebView): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Chess"
      minWidth = 800
      minHeight = 1000
      scene = new Scene {
        root = new BorderPane {
          center = webView
        }
      }
    }
  }

  override def update(gameState: GameStateInterface): Unit = {
    Platform.runLater(() => {
      updateGuiWithGameState(gameState)
    })
  }

  private def updateGuiWithGameState(
      gameState: GameStateInterface,
      availableMoves: List[PositionInterface] = List()
  ): Unit = {
    val renderer = new Renderer()
    val winner = gameState.getField().isCheckMate()
    val html = renderer.render(
      gameState,
      availableMoves,
      winner.map(_.toString).getOrElse("None")
    )
    val decoded = java.net.URLEncoder.encode(html, "UTF-8")
    webEngine.executeScript(
      s"""document.body.innerHTML = decodeURIComponent('${decoded}').replace(/\\+/g, ' ');"""
    )
  }
}
