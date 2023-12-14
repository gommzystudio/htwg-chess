import util.View;
import controller.Controller;
import view.TUI;
import view.GUI;

@main
def start = {
  val controller: Controller = Controller();
  val tui: TUI = TUI(controller);
  val gui: GUI = GUI(controller);

  // start in new thread
  new Thread(() => {
    gui.main(Array());
  }).start();
  tui.startView();
}
