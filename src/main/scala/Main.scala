import com.google.inject.{Guice, Injector}
import controller.{ControllerBaseImpl, ControllerInterface}
import view.{GUI, TUI}

@main
def start = {
  val injector: Injector = Guice.createInjector(new SchachModule)
  val mattInjector: Injector = Guice.createInjector(new SchachMattModule)
  val xmlIjector: Injector = Guice.createInjector(new SchachXmlModule)
  val jsonInjector: Injector = Guice.createInjector(new SchachJsonModule)

  val controller =
    injector.getInstance(classOf[ControllerInterface])

  val tui: TUI = TUI(controller);
  val gui: GUI = GUI(controller);

  new Thread(() => {
    gui.main(Array());
  }).start();
  tui.startView();
}
