package cs3500.reversi.player;

import cs3500.reversi.controller.IPlayerFeature;
import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IReversiModel;

/**
 * An interface to represent a player.
 * Maybe a human player or an AI Player
 */
public interface IPlayer {

  /**
   * Adds a listener to the player.
   *
   * @param listener the listener to be added.
   */
  void addListener(IPlayerFeature listener);

  /**
   * Plays a move for the player. This method is called by listenForMove when the player gets
   * notified of its turn.
   */
  void playMove();

  /**
   * Sets the model for the player.
   *
   * @param tokenColor the color of the player.
   */
  void listenForMove(TokenColor tokenColor);
}