package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.Color;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.GreedyStrat;
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
    IPlayer aiPlayer = new AIPlayer(Color.BLACK, new GreedyStrat(Color.BLACK));
    IPlayer humanPlayer = new HumanPlayer(Color.WHITE);
    Controller controller1 = new Controller(model, black_view, aiPlayer);
    Controller controller2 = new Controller(model, white_view, humanPlayer);

    controller1.controllerGo();
    controller2.controllerGo();
  }
}
