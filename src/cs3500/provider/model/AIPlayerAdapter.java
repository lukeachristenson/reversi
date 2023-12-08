package cs3500.provider.model;


import cs3500.provider.strategy.ReversiStrategy;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;


/**
 * Adapter class for AI Player in a Reversi game.
 * This class adapts an AI player using a specific strategy to interact with different model
 * implementations of the Reversi game. It implements the Player interface to allow for polymorphic
 * interaction with other player types.
 */
public class AIPlayerAdapter implements Player {

  private final int playerNumber;
  private final ReversiStrategy strategy;

  /**
   * Constructs an AIPlayerAdapter.
   * Initializes an AI player with a specified color, strategy, and model.
   * The player number is determined based on the TokenColor.
   *
   * @param tokenColor the TokenColor representing the AI player's token color.
   * @param strategy   the ReversiStrategy defining the AI player's playing strategy.
   * @param model      the IReversiModel representing the game model.
   */
  public AIPlayerAdapter(TokenColor tokenColor, ReversiStrategy strategy,
                         IReversiModel model) {
    this.playerNumber = (tokenColor == TokenColor.BLACK) ? 1 : 2;
    ReadonlyReversiModel model1 = new ModelAdapter(model);
    this.strategy = strategy;
  }

  @Override
  public CubicCoordinate play(ReadonlyReversiModel model) {
    if (model.isGameOver()) {
      System.err.println("Game over");
      System.exit(0);
    }

    try {
      // Delay before move is made.
      Thread.sleep(500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return this.getPlayerStrategy().chooseCoord(model, this.getPlayerNumber());
  }

  @Override
  public int getPlayerNumber() {
    return this.playerNumber;
  }

  @Override
  public ReversiStrategy getPlayerStrategy() {
    return this.strategy;
  }

}
