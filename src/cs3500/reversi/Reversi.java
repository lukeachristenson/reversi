package cs3500.reversi;

import model.HexagonReversi;
import model.IReversiModel;
import view.BasicReversiView;
/**
 * A class to run the Reversi game. Calls a standard game of reversi with a hexagonal board of
 * side length 6 and a basic view for the black player.
 */
public final class Reversi {
  /**
   * Runs a standard game of reversi with a hexagonal board of side length 6 and a basic view for
   * the black player.
   * @param args  the command line arguments
   */
  public static void main(String[] args) {
    IReversiModel model = new HexagonReversi(6);
    BasicReversiView  view = new BasicReversiView(model, model.getCurrentColor());
    view.setVisible(true);
  }
}
