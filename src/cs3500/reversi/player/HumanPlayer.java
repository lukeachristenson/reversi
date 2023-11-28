package cs3500.reversi.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.TokenColor;

/**
 * A class to represent a human player. The class does not perform any actions on the model through
 * the controller(listener) and the moves are instead played by the view inputs that the human user
 * provides. This class has been made solely for the purpose of separating an AI Player from a Human
 * and to provide for code extensibility for additional features that one might add in the future.
 */
public class HumanPlayer implements IPlayer {
  private final TokenColor tokenColor;
  private List<PlayerFeatures> listeners;

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

