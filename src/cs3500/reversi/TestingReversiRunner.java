package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.Color;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.view.BasicReversiView;
import cs3500.reversi.view.ReversiView;

/**
 * A class to run the Reversi game.
 * Note: This class is only made to run the game and manually test the view and strategies.
 */
public class TestingReversiRunner {

  /**
   * The main method to run the game.
   *
   * @param args the arguments to run the game with
   */
  public static void main(String[] args) {
    IReversiModel model = new HexagonReversi(6);// Feel free to customize this as desired
    ReversiView black_view = new BasicReversiView(model, Color.BLACK);
    ReversiView white_view = new BasicReversiView(model, Color.WHITE);
    Controller controller = new Controller(model, black_view, white_view);

    BasicReversiView view = new BasicReversiView(model, Color.BLACK);
    //    view.setVisible(true);
    controller.controllerGo();
  }
}
