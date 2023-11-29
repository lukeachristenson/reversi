package mockplayerview;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.Strategy;

/**
 * This class represents a mock player for testing purposes. Keeps a log of when the methods are
 * called by the controller(s).
 */
public class MockPlayer implements IPlayer {
  private List<IPlayerFeature> listeners;
  private final StringBuilder log;

  /**
   * Constructor for a mockPlayer
   * @param log the log to keep track of when methods are called.
   */
  public MockPlayer(StringBuilder log) {
    this.listeners = new ArrayList<>();
    this.log = log;
  }

  @Override
  public void addListener(IPlayerFeature listener) {
    this.log.append("Player: addListener called\n");
    this.listeners.add(listener);
  }

  @Override
  public void playMove() {
    this.log.append("Player: playMove called\n");
  }

  @Override
  public void listenForMove(TokenColor tokenColor) {
    this.log.append("Player: listenForMove called\n");
  }

}
