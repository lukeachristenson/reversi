import controller.Controller;
import model.HexagonCell;
import model.HexagonReversi;
import model.IReversiModel;
import view.BasicReversiView;
import view.ReversiView;

public class ReversiRunner {
  public static void main(String[] args) {
    IReversiModel model = new HexagonReversi(6);// Feel free to customize this as desired
    ReversiView view = new BasicReversiView(model);
    Controller controller = new Controller(model, view);
    controller.go();
  }
}
