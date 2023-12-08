package cs3500.reversi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.*;

import cs3500.provider.controller.ControllerAdapter;
import cs3500.provider.model.AIPlayerAdapter;
import cs3500.provider.model.HumanPlayerAdapter;
import cs3500.provider.model.ModelAdapter;
import cs3500.provider.model.Player;
import cs3500.provider.model.ReversiModel;
import cs3500.provider.strategy.AvoidCornerNeighborStrategy;
import cs3500.provider.strategy.CheckCornersFirstStrategy;
import cs3500.provider.strategy.GoForCornersStrategy;
import cs3500.provider.strategy.RandomValidMoveStrategy;
import cs3500.provider.strategy.ReversiStrategy;
import cs3500.provider.view.GUI;
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
import cs3500.reversi.strategy.RandomStrat;
import cs3500.reversi.strategy.SandwichStrat;
import cs3500.reversi.strategy.Strategy;
import cs3500.reversi.strategy.UpperLeftStrat;
import cs3500.reversi.view.BasicReversiView;
import cs3500.reversi.view.ReversiView;

/**
 * The main class for running the Reversi game.
 * It sets up the game with a hexagonal board of side length 6 and initializes the views and
 * controllers for players. The players can be either human or AI, depending on the specified
 * strategies.
 */

public final class Reversi {
  /**
   * The main method to run the Reversi game.
   * It expects two arguments representing the strategies for two players.
   * It initializes the models, views, and controllers for both players and starts the game.
   *
   * @param args the arguments to run the game with. Expected to contain strategy identifiers
   *             for the black and white players.
   */
  public static void main(String[] args) {
    if(args.length != 2) {
      System.err.println("Error: Invalid number of arguments, expected 2 but got: " + args.length);
      System.exit(0);
    }

    Optional<Strategy> strategy1;
    IReversiModel model = new HexagonReversi(6);

    initiateOurModel(args, model);


    // Create the second view and controller
    Optional<ReversiStrategy> strategy2 = getProviderStrategy(args[1], TokenColor.WHITE);
    ReversiModel theirModel = new ModelAdapter(model);
    GUI view2 = new GUI(theirModel);
    Player player2 = (strategy2.isPresent())?
            new AIPlayerAdapter(TokenColor.WHITE, strategy2.get(), model) :
            new HumanPlayerAdapter(TokenColor.WHITE);
    ControllerAdapter controller2 = new ControllerAdapter(theirModel, model,
            TokenColor.WHITE, view2, player2);


    JFrame frame2 = new JFrame("Reversi Game - Player 2");
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame2.add(view2);
    frame2.setSize(1000, 1000);
    frame2.setVisible(true);
    theirModel.startGame(6, 2);
    model.startGame();
  }

  /**
   * Initializes the model for the first player (black player).
   * Sets up the player as AI or human based on the provided strategy and
   * creates the corresponding view and controller.
   *
   * @param args the command line arguments.
   * @param model the game model to be used for the player.
   */
  private static void initiateOurModel(String[] args, IReversiModel model) {
    Optional<Strategy> strategy1 = getOurStrategy(args[0], TokenColor.BLACK);
    ReversiView black_view = new BasicReversiView(model, TokenColor.BLACK);
    IPlayer player1 = (strategy1.isPresent()) ? new AIPlayer(TokenColor.BLACK, strategy1.get(),
            model) : new HumanPlayer(TokenColor.BLACK);
    Controller controller1 = new Controller(model, black_view, player1, TokenColor.BLACK);
  }

  /**
   * Retrieves the strategy for the provider based on the given argument.
   * Maps string identifiers to specific strategies for the provider's player.
   *
   * @param arg the strategy identifier for the provider's player.
   * @param tokenColor the token color for the player.
   * @return an Optional containing the strategy if it exists, or an empty Optional for a human player.
   */
  private static Optional<ReversiStrategy> getProviderStrategy(String arg, TokenColor tokenColor) {
    Map<String, ReversiStrategy> strategyMap = new HashMap<>();
    strategyMap.put("provider1", new AvoidCornerNeighborStrategy());
    strategyMap.put("provider2", new RandomValidMoveStrategy());
    strategyMap.put("provider3", new GoForCornersStrategy());
    strategyMap.put("provider4", new CheckCornersFirstStrategy());

    if (!strategyMap.containsKey(arg)) {
      if (arg.equals("h")) {
        return Optional.empty();
      } else {
        System.err.println("Error: Invalid strategy - " + arg);
        System.exit(0);
      }
    }
    return Optional.of(strategyMap.get(arg));
  }

  /**
   * Retrieves the strategy for our implementation based on the given argument.
   * Maps string identifiers to specific strategies for our player.
   *
   * @param arg the strategy identifier for our player.
   * @param tokenColor the token color for the player.
   * @return an Optional containing the strategy if it exists, or an empty Optional for a human player.
   */
  private static Optional<Strategy> getOurStrategy(String arg, TokenColor tokenColor) {
    Map<String, Optional<Strategy>> strategyMap = new HashMap<>();
    strategyMap.put("g", Optional.of(new GreedyStrat(tokenColor)));
    strategyMap.put("u", Optional.of(new UpperLeftStrat(tokenColor)));
    strategyMap.put("a", Optional.of(new AvoidEdgesStrat(tokenColor)));
    strategyMap.put("cc", Optional.of(new ChooseCornersStrat(tokenColor)));
    strategyMap.put("mm", Optional.of(new MiniMaxStrategy(tokenColor)));
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