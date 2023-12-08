package cs3500.provider.model;

import cs3500.provider.strategy.ReversiStrategy;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.HumanPlayer;

/**
 * Adapter class for a human player in a Reversi game.
 * This class extends the HumanPlayer class and implements the Player interface, adapting
 * a human player to the required player interface for a given Reversi game model.
 * It provides necessary methods to interact with the game model and process player actions.
 */
public class HumanPlayerAdapter extends HumanPlayer implements Player {
  private final int playerNumber;

  /**
   * Constructs a HumanPlayerAdapter.
   * Initializes a human player with the specified token color.
   * The player number is determined based on the TokenColor.
   *
   * @param tokenColor the TokenColor representing the human player's token color.
   */
  public HumanPlayerAdapter(TokenColor tokenColor) {
    super(tokenColor);
    this.playerNumber = (tokenColor == TokenColor.BLACK) ? 1 : 2;
  }

  @Override
  public CubicCoordinate play(ReadonlyReversiModel model) {
    return null;
  }

  @Override
  public int getPlayerNumber() {
    return this.playerNumber;
  }

  @Override
  public ReversiStrategy getPlayerStrategy() {
    return null;
  }
}
