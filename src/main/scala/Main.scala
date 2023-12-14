import controller.ControllerBaseImpl;
import controller.ControllerInterface;
import view.TUI;
import view.GUI;

@main
def start = {
  val controller: ControllerInterface = ControllerBaseImpl();
  val tui: TUI = TUI(controller);
  val gui: GUI = GUI(controller);

  // start in new thread
  new Thread(() => {
    gui.main(Array());
  }).start();
  tui.startView();
}
