package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
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

  public static void main(String[] args) {
    IReversiModel model = new HexagonReversi(6);// Feel free to customize this as desired
    IPlayer aiPlayer = new AIPlayer(TokenColor.BLACK, new GreedyStrat(TokenColor.BLACK), model);
    IPlayer humanPlayer = new HumanPlayer(TokenColor.WHITE);
    ReversiView black_view = new BasicReversiView(model, TokenColor.BLACK);
    ReversiView white_view = new BasicReversiView(model, TokenColor.WHITE);
    Controller controllerBlack = new Controller(model, black_view, aiPlayer, TokenColor.BLACK);
    Controller controllerWhite = new Controller(model, black_view, humanPlayer, TokenColor.WHITE);
    model.startGame();
  }
}
