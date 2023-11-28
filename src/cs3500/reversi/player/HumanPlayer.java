package cs3500.reversi.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IReversiModel;

/**
 * A class to represent a human player.
 */
public class HumanPlayer implements IPlayer {
  private final TokenColor tokenColor;
  private  List<PlayerFeatures> listeners;

  /**
   * Constructor for a HumanPlayer. Takes in a color and makes a player who will play that color.
   *
   * @param tokenColor the color of the player.
   */
  public HumanPlayer(TokenColor tokenColor) {
    this.tokenColor = tokenColor;
    this.listeners = new ArrayList<>();
  }


  @Override
  public void addListener(PlayerFeatures listener) {
    this.listeners.add(listener);
  }

  @Override
  public void playMove() {
    // Do nothing
  }

  @Override
  public void listenForMove(TokenColor tokenColor) {
    // Do nothing
  }
}

