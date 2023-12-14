package view

import controller.ControllerInterface
import model.position.PositionBaseImpl
import model.position.PositionInterface
import model.gamestate.GameStateInterface
import model.gamestate.GameStateBaseImpl
import util.view.ViewInterface
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.web.{WebEngine, WebView}
import javafx.concurrent.Worker.State
import netscape.javascript.JSObject
import scalafx.application.Platform
import view.html.Renderer
import util.updater.UpdaterInterface

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
                PositionBaseImpl(
                  from.charAt(0).asDigit,
                  from.charAt(1).asDigit
                ),
                PositionBaseImpl(to.charAt(0).asDigit, to.charAt(1).asDigit)
              )
            case "loadMoves" =>
              val pos = args(1)
              val moves = controller
                .getGameSate()
                .getField()
                .getPiece(
                  PositionBaseImpl(pos.charAt(0).asDigit, pos.charAt(1).asDigit)
                )
                .get
                .availableMoves(
                  PositionBaseImpl(
                    pos.charAt(0).asDigit,
                    pos.charAt(1).asDigit
                  ),
                  controller.getGameSate().getField()
                )
              updateGuiWithGameState(controller.getGameSate(), moves)
            case "undo" => controller.undoCommand()
            case "redo" => controller.redoCommand()
            case _      => println("Unknown command")
          }
        }

        controller.addViewAndUpdate(this)
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
      gameState: GameStateInterface
  ): Unit = {
    Platform.runLater(() => {
      updateGuiWithGameState(gameState, List())
    })
  }

  private def updateGuiWithGameState(
      gameState: GameStateInterface,
      availableMoves: List[PositionInterface] = List()
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
