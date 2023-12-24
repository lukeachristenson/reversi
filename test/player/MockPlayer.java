package player;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.IPlayerFeature;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.Strategy;

/**
 * A class to represent a human player.
 */
public class MockPlayer implements IPlayer {
  private final TokenColor tokenColor;
  private final List<IPlayerFeature> listeners;
  private final StringBuilder log;
  private final ROModel model;

  /**
   * Constructor for a HumanPlayer. Takes in a color and makes a player who will play that color.
   *
   * @param tokenColor    the color of the player.
   * @param strategy the strategy of the player.
   */
  public MockPlayer(TokenColor tokenColor, Strategy strategy, StringBuilder log, ROModel model) {
    this.tokenColor = tokenColor;
    this.listeners = new ArrayList<>();
    this.log = log;
    this.model = model;
  }

  @Override
  public void addListener(IPlayerFeature listener) {
    this.listeners.add(listener);
  }

  @Override
  public void playMove() {
    log.append("playMove called with moves list of size: " + model.getValidMoves(tokenColor).size()
            + "\n");
  }


  @Override
  public void listenForMove(TokenColor tokenColor) {
    // Play the move if it is the AI's turn.
    log.append("listenForMove called with color: ").append(tokenColor.toString()).append("\n");
    if (tokenColor == this.tokenColor) {
      this.playMove();
    }
  }
}