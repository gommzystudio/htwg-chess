import controller.ControllerBaseImpl;
import controller.ControllerInterface;
import view.TUI;
import view.GUI;
import Matt.{given};
import model.gamestate.GameStateInterface
import util.fileio.FileIO

@main
def start = {
  val controller = ControllerBaseImpl();

  val tui: TUI = TUI(controller);
  val gui: GUI = GUI(controller);

  // start in new thread
  new Thread(() => {
    gui.main(Array());
  }).start();
  tui.startView();
}
