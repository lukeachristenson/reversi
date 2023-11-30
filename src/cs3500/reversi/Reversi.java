package cs3500.reversi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
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
import cs3500.reversi.view.BasicReversiView;
import cs3500.reversi.view.ReversiView;

/**
 * A class to run the Reversi game. Calls a standard game of reversi with a hexagonal board of
 * side length 6 and a basic view for the black player.
 */
public final class Reversi {
  /**
   * The main method to run the game.
   *
   * @param args the arguments to run the game with
   */
  public static void main(String[] args) {
    Optional<Strategy> strat1;
    Optional<Strategy> strat2;

    if (args.length != 2) {
      System.err.println("Invalid number of arguments, expected 2 but got: " + args.length);
      System.exit(0);
    }
    strat1 = getStrategy(args[0], TokenColor.BLACK);
    strat2 = getStrategy(args[1], TokenColor.WHITE);

    IReversiModel model = initiateModel(6, strat1, strat2);
    model.startGame();
  }

  private static IReversiModel initiateModel(int size,
                                             Optional<Strategy> strategy1,
                                             Optional<Strategy> strategy2) {
    IReversiModel model = new HexagonReversi(size);
    ReversiView black_view = new BasicReversiView(model, TokenColor.BLACK);
    ReversiView white_view = new BasicReversiView(model, TokenColor.WHITE);

    IPlayer player1 = (strategy1.isPresent()) ? new AIPlayer(TokenColor.BLACK, strategy1.get(),
            model) : new HumanPlayer(TokenColor.BLACK);
    IPlayer player2 = (strategy2.isPresent()) ? new AIPlayer(TokenColor.WHITE, strategy2.get(),
            model) : new HumanPlayer(TokenColor.WHITE);


    Controller controller1 = new Controller(model, black_view, player1, TokenColor.BLACK);
    Controller controller2 = new Controller(model, white_view, player2, TokenColor.WHITE);
    return model;
  }

  private static Optional<Strategy> getStrategy(String arg, TokenColor tokenColor) {
    Map<String, Optional<Strategy>> strategyMap = new HashMap<>();
    strategyMap.put("g", Optional.of(new GreedyStrat(tokenColor)));
    strategyMap.put("u", Optional.of(new UpperLeftStrat(tokenColor)));
    strategyMap.put("a", Optional.of(new AvoidEdgesStrat(tokenColor)));
    strategyMap.put("cc", Optional.of(new ChooseCornersStrat(tokenColor)));
    strategyMap.put("mm", Optional.of(new MiniMaxStrategy(tokenColor)));
    strategyMap.put("oa", Optional.of(new OurAlgorithmStrat(tokenColor)));
    strategyMap.put("r", Optional.of(new RandomStrat(tokenColor)));
    strategyMap.put("san1", Optional.of((new SandwichStrat(tokenColor,
            List.of(strategyMap.get("g").get(),
                    strategyMap.get("a").get(),
                    strategyMap.get("cc").get())))));
    strategyMap.put("san2", Optional.of(new SandwichStrat(tokenColor,
            List.of(strategyMap.get("mm").get(),
                    strategyMap.get("g").get()))));
    strategyMap.put("san3", Optional.of(new SandwichStrat(tokenColor,
            List.of(strategyMap.get("mm").get(),
                    strategyMap.get("g").get(), strategyMap.get("a").get()))));
    strategyMap.put("san4",
            Optional.of(new SandwichStrat(tokenColor,
                    List.of(strategyMap.get("mm").get(),
                            strategyMap.get("g").get(),
                            strategyMap.get("a").get(),
                            strategyMap.get("cc").get()))));

    if (!strategyMap.containsKey(arg)) {
      if (arg.equals("h")) {
        return Optional.empty();
      } else {
        System.err.println("Error: Invalid strategy - " + arg);
        System.exit(0);
      }
    }
    return strategyMap.get(arg);
  }
}
