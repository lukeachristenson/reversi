package view;


import model.ICell;

/**
 * A marker interface for all view features to be used in the Reversi game.
 */
public interface ViewFeatures {
  /**
   * Plays a move at the given cell.
   * @param cell
   */
  void playMove(ICell cell);
  /**
   * Passes the turn.
   */
  void pass();
  /**
   * Quits the game.
   */
  void quit();
}
