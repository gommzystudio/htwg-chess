import com.google.inject.{Guice, Injector}
import controller.{ControllerBaseImpl, ControllerInterface}
import view.{GUI, TUI}

@main
def start = {
  // For a new game, use the following injector:
  val injector: Injector = Guice.createInjector(new SchachModule)
  // For a game with checkmate, use the following injector:
  val mattInjector: Injector = Guice.createInjector(new SchachMattModule)

  val controller =
    injector.getInstance(classOf[ControllerInterface])

  val tui: TUI = TUI(controller);
  val gui: GUI = GUI(controller);

  // Start the GUI and TUI in separate threads.
  new Thread(() => {
    gui.main(Array());
  }).start();
  tui.startView();
}
