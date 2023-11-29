package player;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.IPlayerFeature;
import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.Strategy;

/**
 * A class to represent a human player.
 */
public class MockPlayer implements IPlayer {
  private final TokenColor tokenColor;
  private List<IPlayerFeature> listeners;
  private final StringBuilder log;
  private final Strategy strategy;

  /**
   * Constructor for a HumanPlayer. Takes in a color and makes a player who will play that color.
   *
   * @param tokenColor    the color of the player.
   * @param strategy the strategy of the player.
   */
  public MockPlayer(TokenColor tokenColor, Strategy strategy, StringBuilder log) {
    this.tokenColor = tokenColor;
    this.strategy = strategy;
    this.listeners = new ArrayList<>();
    this.log = log;
  }

  @Override
  public void addListener(IPlayerFeature listener) {
    this.listeners.add(listener);
  }

  @Override
  public void playMove() {

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