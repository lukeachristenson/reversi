package cs3500.reversi.view;


import cs3500.reversi.model.ICell;

/**
 * A marker interface for all view features to be used in the Reversi game.
 */
public interface ViewFeatures {
  /**
   * Plays a move at the given cell.
   *
   * @param cell the cell to play the move at.
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
