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

// GUI class using JavaFX for graphical user interface.
// Problem: Only working way for communication between Scala and JavaScript are alerts. But it works fine.
class GUI(controller: ControllerInterface)
    extends JFXApp3
    with ViewInterface(controller.asInstanceOf[UpdaterInterface]) {

  private var webEngine: WebEngine = _

  // Start method to initialize the GUI.
  override def start(): Unit = {
    val webView = new WebView()
    setupWebView(webView)
    setupStage(webView)
  }

  // Sets up the web view component.
  private def setupWebView(webView: WebView): Unit = {
    webView.engine.loadContent(loadHtmlContent())
    setupEngineListener(webView.engine)
    webEngine = webView.engine
  }

  // Loads HTML content for the web view.
  private def loadHtmlContent(): String = {
    new Renderer().defaultStructure()
  }

  // Sets up a listener for the web engine.
  private def setupEngineListener(engine: WebEngine): Unit = {
    engine.getLoadWorker.stateProperty.addListener((_, _, newState) => {
      if (newState == State.SUCCEEDED) {
        setupAlertHandler(engine)
        controller.addViewAndUpdate(this)
      }
    })
  }

  // Sets up an alert handler for JavaScript alerts in the web engine.
  private def setupAlertHandler(engine: WebEngine): Unit = {
    engine.onAlert = { event => handleAlert(event.getData) }
  }

  // Handles alerts and executes appropriate actions based on alert data.
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

  // Processes a move command.
  private def processMoveCommand(from: String, to: String): Unit = {
    val fromPos =
      PositionBaseImpl(from.charAt(0).asDigit, from.charAt(1).asDigit)
    val toPos = PositionBaseImpl(to.charAt(0).asDigit, to.charAt(1).asDigit)
    controller.runMoveCommand(fromPos, toPos)
  }

  // Processes a command to load possible moves for a piece.
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

  // Filters out moves that would put the player in check.
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

  // Sets up the main stage for the application.
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

  // Updates the GUI based on the current game state.
  override def update(gameState: GameStateInterface): Unit = {
    Platform.runLater(() => {
      updateGuiWithGameState(gameState)
    })
  }

  // Updates the web view with the current game state and available moves.
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
