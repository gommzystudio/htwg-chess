import controller.ControllerBaseImpl;
import controller.ControllerInterface;
import view.TUI;
import view.GUI;
import com.google.inject.Injector
import com.google.inject.Guice

@main
def start = {
  val injector: Injector = Guice.createInjector(new SchachModule)
  val mattInjector: Injector = Guice.createInjector(new SchachMattModule)
  val xmlIjector: Injector = Guice.createInjector(new SchachXmlModule)
  val jsonInjector: Injector = Guice.createInjector(new SchachJsonModule)

  val controller =
    injector.getInstance(classOf[ControllerInterface])

  controller.load();

  val tui: TUI = TUI(controller);
  val gui: GUI = GUI(controller);

  // start in new thread
  new Thread(() => {
    gui.main(Array());
  }).start();
  tui.startView();
}
