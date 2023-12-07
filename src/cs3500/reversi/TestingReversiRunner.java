package cs3500.reversi;

import cs3500.provider.controller.ControllerAdapter;
import cs3500.provider.controller.ControllerFeatures;
import cs3500.provider.model.ModelAdapter;
import cs3500.provider.model.ReversiModel;
import cs3500.provider.view.GUI;
import cs3500.provider.view.ViewInterface;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.IPlayer;

/**
 * A class to run the Reversi game. Calls a standard game of reversi with a hexagonal board of
 * side length 6 and a basic view for the black player.
 */
public final class TestingReversiRunner {
  /**
   * The main method to run the game.
   *
   * @param args the arguments to run the game with
   */
  public static void main(String[] args) {
    IReversiModel ourModel = new HexagonReversi(6);
    ReversiModel theirModel = new ModelAdapter(ourModel);
    ViewInterface view = new GUI(theirModel);

    ControllerFeatures controller = new ControllerAdapter(theirModel, TokenColor.BLACK, view);
    theirModel.startGame(6, 2);
  }

}
