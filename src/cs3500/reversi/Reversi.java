package cs3500.reversi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.AvoidEdgesStrat;
import cs3500.reversi.strategy.ChooseCornersStrat;
import cs3500.reversi.strategy.GreedyStrat;
import cs3500.reversi.strategy.MiniMaxStrategy;
import cs3500.reversi.strategy.OurAlgorithmStrat;
import cs3500.reversi.strategy.RandomStrat;
import cs3500.reversi.strategy.SandwichStrat;
import cs3500.reversi.strategy.Strategy;
import cs3500.reversi.strategy.UpperLeftStrat;
import cs3500.reversi.view.HexagonReversiView;
import cs3500.reversi.view.ReversiView;
import cs3500.reversi.view.SquareReversiView;

/**
 * A class to run the Reversi game. Calls a standard game of reversi with a hexagonal or square
 * board and a basic view for the black player.
 */
public final class Reversi {
  /**
   * The main method to run the game.
   *
   * @param args the arguments to run the game with
   */
  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Invalid number of arguments, expected 3 but got: " + args.length);
      System.exit(0);
    }

    String gameType = args[0].toLowerCase();
    String blackStrategyArg = args[1];
    String whiteStrategyArg = args[2];

    Optional<Strategy> strat1 = getStrategy(blackStrategyArg, TokenColor.BLACK);
    Optional<Strategy> strat2 = getStrategy(whiteStrategyArg, TokenColor.WHITE);

    IReversiModel model;
    ReversiView blackView;
    ReversiView whiteView;
    if (gameType.equals("h")) {
      model = new HexagonReversi(6); // You can adjust the size as needed
      blackView = new HexagonReversiView(model, TokenColor.BLACK);
      whiteView = new HexagonReversiView(model, TokenColor.WHITE);
    } else if (gameType.equals("s")) {
      model = new SquareReversi(8); // You can adjust the size as needed
      blackView = new SquareReversiView(model, TokenColor.BLACK);
      whiteView = new SquareReversiView(model, TokenColor.WHITE);
    } else {
      System.err.println("Invalid game type. Use 's' for square or 'h' for hexagonal.");
      System.exit(0);
      return; // Added return to exit the program
    }


    IPlayer player1 = (strat1.isPresent()) ? new AIPlayer(TokenColor.BLACK, strat1.get(), model)
            : new HumanPlayer(TokenColor.BLACK);
    IPlayer player2 = (strat2.isPresent()) ? new AIPlayer(TokenColor.WHITE, strat2.get(), model)
            : new HumanPlayer(TokenColor.WHITE);

    Controller controller1 = new Controller(model, blackView, player1, TokenColor.BLACK);
    Controller controller2 = new Controller(model, whiteView, player2, TokenColor.WHITE);

    model.startGame();
  }

  private static Optional<Strategy> getStrategy(String arg, TokenColor tokenColor) {
    if (arg.equals("h")) {
      return Optional.empty();
    }

    Map<String, Strategy> strategyMap = new HashMap<>();
    strategyMap.put("g", new GreedyStrat(tokenColor));
    strategyMap.put("u", new UpperLeftStrat(tokenColor));
    strategyMap.put("a", new AvoidEdgesStrat(tokenColor));
    strategyMap.put("cc", new ChooseCornersStrat(tokenColor));
    strategyMap.put("mm", new MiniMaxStrategy(tokenColor));
    strategyMap.put("oa", new OurAlgorithmStrat(tokenColor));
    strategyMap.put("r", new RandomStrat(tokenColor));
    strategyMap.put("san1", new SandwichStrat(tokenColor, List.of(strategyMap.get("g"),
            strategyMap.get("a"), strategyMap.get("cc"))));
    strategyMap.put("san2", new SandwichStrat(tokenColor, List.of(strategyMap.get("mm"),
            strategyMap.get("g"))));
    strategyMap.put("san3", new SandwichStrat(tokenColor, List.of(strategyMap.get("mm"),
            strategyMap.get("g"), strategyMap.get("a"))));
    strategyMap.put("san4", new SandwichStrat(tokenColor, List.of(strategyMap.get("mm"),
            strategyMap.get("g"), strategyMap.get("a"), strategyMap.get("cc"))));

    if (strategyMap.containsKey(arg)) {
      return Optional.of(strategyMap.get(arg));
    } else {
      throw new IllegalArgumentException("Invalid strategy: " + arg);
    }
  }
}
