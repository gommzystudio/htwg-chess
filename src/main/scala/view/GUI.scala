package view

import controller.Controller
import model.{GameState, Position}
import util.View
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.web.{WebEngine, WebView}
import javafx.concurrent.Worker.State
import netscape.javascript.JSObject
import scalafx.application.Platform
import view.html.Renderer

class GUI(controller: Controller) extends JFXApp3 with View(controller) {
  private var webEngine: WebEngine = _

  override def start(): Unit = {
    val webView = new WebView()
    setupWebView(webView)
    setupStage(webView)
  }

  private def setupWebView(webView: WebView): Unit = {
    webView.engine.loadContent(loadHtmlContent())

    webView.engine.getLoadWorker.stateProperty.addListener((_, _, newState) => {
      if (newState == State.SUCCEEDED) {
        webView.engine.onAlert = { (event) =>
          val args = event.getData().split(";")
          val command = args(0)
          args(0) match {
            case "move" =>
              val from = args(1)
              val to = args(2)
              controller.runMoveCommand(
                Position(from.charAt(0).asDigit, from.charAt(1).asDigit),
                Position(to.charAt(0).asDigit, to.charAt(1).asDigit)
              )
            case "loadMoves" =>
              val pos = args(1)
              val moves = controller.gameState
                .getField()
                .getPiece(
                  Position(pos.charAt(0).asDigit, pos.charAt(1).asDigit)
                )
                .get
                .availableMoves(
                  Position(pos.charAt(0).asDigit, pos.charAt(1).asDigit),
                  controller.gameState.getField()
                )
              updateGuiWithGameState(controller.gameState, moves)
            case "undo" => controller.undoCommand()
            case "redo" => controller.redoCommand()
            case _      => println("Unknown command")
          }
        }

        controller.addView(this)
      }
    })

    webEngine = webView.engine
  }

  private def loadHtmlContent(): String = {
    val renderer = new Renderer()
    val html = renderer.defaultStructure()
    return html
  }

  private def setupStage(webView: WebView): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Chess"
      scene = new Scene {
        root = new BorderPane {
          center = webView
        }
      }
    }
  }

  override def update(
      gameState: GameState
  ): Unit = {
    Platform.runLater(() => {
      updateGuiWithGameState(gameState, List())
    })
  }

  private def updateGuiWithGameState(
      gameState: GameState,
      availableMoves: List[Position] = List()
  ): Unit = {
    try {
      val renderer = new Renderer()
      val html = renderer.render(gameState, availableMoves)
      var decoded = java.net.URLEncoder.encode(html, "UTF-8")

      webEngine.executeScript(
        s"""document.body.innerHTML = decodeURIComponent('${decoded}').replace(/\\+/g, ' ');"""
      )
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
