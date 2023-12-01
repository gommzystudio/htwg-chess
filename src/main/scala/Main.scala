import util.View;
import controller.Controller;
import view.TUI;

@main
def start = {
  val controller: Controller = Controller();
  val view: View = TUI(controller);
  controller.startGame();
  view.startView();
}
