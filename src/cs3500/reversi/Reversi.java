package cs3500.reversi;

import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.view.BasicReversiView;

/**
 * A class to run the Reversi game. Calls a standard game of reversi with a hexagonal board of
 * side length 6 and a basic view for the black player.
 */
public final class Reversi {
  /**
   * Runs a standard game of reversi with a hexagonal board of side length 6 and a basic view for
   * the black player.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    int numSides = 6; // Default value.

    IReversiModel model = new HexagonReversi(6);
    BasicReversiView view = new BasicReversiView(model, model.getCurrentColor());
    view.setVisible(true);
  }
}
