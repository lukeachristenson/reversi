package cs3500.reversi;

import javax.swing.*;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.Color;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.player.Player;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.AvoidEdges;
import cs3500.reversi.strategy.ChooseCorners;
import cs3500.reversi.strategy.GreedyStrat;
import cs3500.reversi.strategy.HumanStrat;
import cs3500.reversi.strategy.MiniMaxStrategy;
import cs3500.reversi.strategy.OurAlgorithm;
import cs3500.reversi.strategy.Strategy;
import cs3500.reversi.strategy.UpperLeftStrat;
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
      int size = 6;
      Strategy strategy1 = new HumanStrat(Color.BLACK);
      Strategy strategy2 = new HumanStrat(Color.WHITE);

      for (int i = 0; i < args.length; i++) {
        switch (args[i]) {
          case "-s":
            case "--size":
            size = Integer.parseInt(args[i + 1]);
            break;

          case "-p1":
            case "--player1":
            System.out.println(args[i + 1]);
            strategy1 = getStrategy(args[i + 1], Color.BLACK);
            break;

          case "-p2":
            case "--player2":
            strategy2 = getStrategy(args[i + 1], Color.WHITE);
            break;

        }
      }

    IReversiModel model = new HexagonReversi(size);
    ReversiView black_view = new BasicReversiView(model, Color.BLACK);
    ReversiView white_view = new BasicReversiView(model, Color.WHITE);
    IPlayer player1 = new Player(Color.BLACK, strategy1);
    IPlayer player2 = new Player(Color.WHITE, strategy2);

    Controller controller1 = new Controller(model, black_view, player1);
    Controller controller2 = new Controller(model, white_view, player2);
    model.addListener(controller1);
    model.addListener(controller2);
    model.startGame();
    controller1.controllerGo();
    controller2.controllerGo();

  }

  private static Strategy getStrategy(String arg, Color color) {
    switch (arg) {
      case "h":
        return new HumanStrat(color);
      case "g":
        System.out.println("Greedy");
        return new GreedyStrat(color);
      case "u":
        return new UpperLeftStrat(color);
      case "a":
        return new AvoidEdges(color);
      case "cc":
        return new ChooseCorners(color);
      case "mm":
        return new MiniMaxStrategy(color);
      case "oa":
        return new OurAlgorithm(color);

      default:
        throw new IllegalArgumentException("Invalid strategy: " + arg);
    }
  }
}
