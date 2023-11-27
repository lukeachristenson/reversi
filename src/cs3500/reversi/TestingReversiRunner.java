package cs3500.reversi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.Color;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.AvoidEdges;
import cs3500.reversi.strategy.ChooseCorners;
import cs3500.reversi.strategy.GreedyStrat;
import cs3500.reversi.strategy.MiniMaxStrategy;
import cs3500.reversi.strategy.OurAlgorithm;
import cs3500.reversi.strategy.RandomStrat;
import cs3500.reversi.strategy.Sandwich;
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
    Optional<Strategy> strategy1 = Optional.empty();
    Optional<Strategy> strategy2 = Optional.empty();

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

    IPlayer player1 = (strategy1.isPresent()) ? new AIPlayer(Color.BLACK, strategy1.get()) : new HumanPlayer(Color.BLACK);
    IPlayer player2 = (strategy2.isPresent()) ? new AIPlayer(Color.WHITE, strategy2.get()) : new HumanPlayer(Color.WHITE);


    Controller controller1 = new Controller(model, black_view, player1);
    Controller controller2 = new Controller(model, white_view, player2);
    model.addListener(controller1);
    model.addListener(controller2);
    model.startGame();
    controller1.controllerGo();
    controller2.controllerGo();
  }

  private static Optional<Strategy> getStrategy(String arg, Color color) {
    Map<String, Strategy> strategyMap = new HashMap<>();
    strategyMap.put("g", new GreedyStrat(color));
    strategyMap.put("u", new UpperLeftStrat(color));
    strategyMap.put("a", new AvoidEdges(color));
    strategyMap.put("cc", new ChooseCorners(color));
    strategyMap.put("mm", new MiniMaxStrategy(color));
    strategyMap.put("oa", new OurAlgorithm(color));
    strategyMap.put("r", new RandomStrat(color));
    strategyMap.put("san1", new Sandwich(color, List.of(strategyMap.get("g"),
            strategyMap.get("a"), strategyMap.get("cc"))));
    strategyMap.put("san2", new Sandwich(color, List.of(strategyMap.get("mm"),
            strategyMap.get("g"))));
    strategyMap.put("san3", new Sandwich(color, List.of(strategyMap.get("mm"),
            strategyMap.get("g"), strategyMap.get("a"))));
    strategyMap.put("san4", new Sandwich(color, List.of(strategyMap.get("mm"), strategyMap.get("g"), strategyMap.get("a"), strategyMap.get("cc"))));

    if(strategyMap.containsKey(arg)){
      return Optional.of(strategyMap.get(arg));
    }
    else {
      throw new IllegalArgumentException("Invalid strategy: " + arg);
    }
  }
}
