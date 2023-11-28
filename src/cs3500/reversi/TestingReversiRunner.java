package cs3500.reversi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.TokenColor;
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
          strategy1 = getStrategy(args[i + 1], TokenColor.BLACK);
          break;

        case "-p2":
        case "--player2":
          strategy2 = getStrategy(args[i + 1], TokenColor.WHITE);
          break;
      }
    }

    IReversiModel model = new HexagonReversi(size);
    ReversiView black_view = new BasicReversiView(model, TokenColor.BLACK);
    ReversiView white_view = new BasicReversiView(model, TokenColor.WHITE);

    IPlayer player1 = (strategy1.isPresent()) ? new AIPlayer(TokenColor.BLACK, strategy1.get(), model) : new HumanPlayer(TokenColor.BLACK);
    IPlayer player2 = (strategy2.isPresent()) ? new AIPlayer(TokenColor.WHITE, strategy2.get(), model) : new HumanPlayer(TokenColor.WHITE);


    Controller controller1 = new Controller(model, black_view, player1, TokenColor.BLACK);
    Controller controller2 = new Controller(model, white_view, player2, TokenColor.WHITE);
    model.startGame();
  }

  private static Optional<Strategy> getStrategy(String arg, TokenColor tokenColor) {
    Map<String, Strategy> strategyMap = new HashMap<>();
    strategyMap.put("g", new GreedyStrat(tokenColor));
    strategyMap.put("u", new UpperLeftStrat(tokenColor));
    strategyMap.put("a", new AvoidEdges(tokenColor));
    strategyMap.put("cc", new ChooseCorners(tokenColor));
    strategyMap.put("mm", new MiniMaxStrategy(tokenColor));
    strategyMap.put("oa", new OurAlgorithm(tokenColor));
    strategyMap.put("r", new RandomStrat(tokenColor));
    strategyMap.put("san1", new Sandwich(tokenColor, List.of(strategyMap.get("g"),
            strategyMap.get("a"), strategyMap.get("cc"))));
    strategyMap.put("san2", new Sandwich(tokenColor, List.of(strategyMap.get("mm"),
            strategyMap.get("g"))));
    strategyMap.put("san3", new Sandwich(tokenColor, List.of(strategyMap.get("mm"),
            strategyMap.get("g"), strategyMap.get("a"))));
    strategyMap.put("san4", new Sandwich(tokenColor, List.of(strategyMap.get("mm"), strategyMap.get("g"), strategyMap.get("a"), strategyMap.get("cc"))));

    if(strategyMap.containsKey(arg)){
      return Optional.of(strategyMap.get(arg));
    }
    else {
      throw new IllegalArgumentException("Invalid strategy: " + arg);
    }
  }
}
