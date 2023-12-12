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
    Strategy strat1;
    Strategy strat2;

    if (args.length != 2) {
      throw new IllegalArgumentException("Invalid number of arguments");
    }
    strat1 = getStrategy(args[0], TokenColor.BLACK).get();
    strat2 = getStrategy(args[1], TokenColor.WHITE).get();

    IReversiModel model = initiateModel(8, Optional.of(strat1), Optional.of(strat2));
    model.startGame();
  }

  private static IReversiModel initiateModel(int size,
                                             Optional<Strategy> strategy1,
                                             Optional<Strategy> strategy2) {
//    IReversiModel model = new HexagonReversi(size);
    IReversiModel model = new SquareReversi(size);
    ReversiView black_view = new SquareReversiView(model, TokenColor.BLACK);
    ReversiView white_view = new SquareReversiView(model, TokenColor.WHITE);

//    IPlayer player1 = (strategy1.isPresent()) ? new AIPlayer(TokenColor.BLACK, strategy1.get(),
//            model) : new HumanPlayer(TokenColor.BLACK);
//    IPlayer player2 = (strategy2.isPresent()) ? new AIPlayer(TokenColor.WHITE, strategy2.get(),
//            model) : new HumanPlayer(TokenColor.WHITE);

    IPlayer player1 = new HumanPlayer(TokenColor.BLACK);
    IPlayer player2 = new HumanPlayer(TokenColor.WHITE);


    Controller controller1 = new Controller(model, black_view, player1, TokenColor.BLACK);
    Controller controller2 = new Controller(model, white_view, player2, TokenColor.WHITE);
    return model;
  }

  private static Optional<Strategy> getStrategy(String arg, TokenColor tokenColor) {
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
