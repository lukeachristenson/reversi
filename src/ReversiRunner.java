import controller.Controller;
import model.Color;
import model.HexagonReversi;
import model.IReversiModel;
import view.BasicReversiView;
import view.ReversiView;

/**
 * A class to run the Reversi game.
 * Note: This class is only made to run the game and manually test the view and strategies.
 */
public class ReversiRunner {
  public static void main(String[] args) {
    IReversiModel model = new HexagonReversi(3);// Feel free to customize this as desired
    ReversiView black_view = new BasicReversiView(model, Color.BLACK);
    ReversiView white_view = new BasicReversiView(model, Color.WHITE);
    Controller controller = new Controller(model, black_view, white_view);

    BasicReversiView view = new BasicReversiView(model, Color.BLACK);
//    view.setVisible(true);
    controller.go();
  }
}
