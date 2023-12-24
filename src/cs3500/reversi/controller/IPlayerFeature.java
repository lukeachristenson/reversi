package cs3500.reversi.controller;

import cs3500.reversi.model.ICell;

/**
 * A interface to represent the features of a player. These are the capabilities that the controller
 * will have to affect the player.
 */
public interface IPlayerFeature {
  /**
   * A method that allows a player to play a move on a given cell.
   *
   * @param cell the cell that the player wants to play a move on.
   */
  public void playMove(ICell cell);

  /**
   * A method that allows a player to pass their turn.
   */
  public void pass();

  /**
   * A method that allows a player to quit the game.
   */
  public void quit();
}
