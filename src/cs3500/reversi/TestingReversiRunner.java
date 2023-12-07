package cs3500.reversi;

import javax.swing.*;

import cs3500.provider.controller.ControllerAdapter;
import cs3500.provider.controller.ControllerFeatures;
import cs3500.provider.model.ModelAdapter;
import cs3500.provider.model.ReversiModel;
import cs3500.provider.view.GUI;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;

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

    // Create the first view and controller
    ReversiModel theirModel1 = new ModelAdapter(ourModel);
    GUI view1 = new GUI(theirModel1);
    ControllerFeatures controller1 = new ControllerAdapter(theirModel1, TokenColor.BLACK, view1);

    JFrame frame1 = new JFrame("Reversi Game - Player 1");
    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.add(view1);
    frame1.setSize(500, 500);
    frame1.setVisible(true);

    // Create the second view and controller
    GUI view2 = new GUI(theirModel1);
    ControllerFeatures controller2 = new ControllerAdapter(theirModel1, TokenColor.WHITE, view2);
    theirModel1.startGame(6, 2);

    JFrame frame2 = new JFrame("Reversi Game - Player 2");
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame2.add(view2);
    frame2.setSize(500, 500);
    frame2.setVisible(true);
    theirModel1.startGame(6, 2);

  }
}
