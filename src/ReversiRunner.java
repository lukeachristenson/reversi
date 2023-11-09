import controller.Controller;
import model.Color;
import model.HexagonReversi;
import model.IReversiModel;
import view.BasicReversiView;
import view.ReversiView;

public class ReversiRunner {
  public static void main(String[] args) {
    IReversiModel model = new HexagonReversi(6);// Feel free to customize this as desired
    ReversiView black_view = new BasicReversiView(model, Color.BLACK);
    ReversiView white_view = new BasicReversiView(model, Color.WHITE);
    Controller controller = new Controller(model, black_view, white_view);
    controller.go();
  }
}
