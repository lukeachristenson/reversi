import java.util.List;

/**
 * This interface represents a model for a game of Reversi.
 */
public interface ReversiModel {
  /**
   * Returns the dimensions of the board which varies depending on the type of board.
   *
   * @return the dimensions of the board as a list of integers.
   */
  List<Integer> getDimensions();

  /**
   * Returns the cell at the given row and column.
   *
   * @param row the row of the cell to return
   * @param col the column of the cell to return
   * @return the cell at the given row and column
   * @throws IllegalArgumentException if the given row or column is out of bounds
   */
  ICell getCellAt(int row, int col) throws IllegalArgumentException;

  /**
   * Returns the current score of the given player.
   *
   * @param player the player whose score to return
   * @return the current score of the given player
   * @throws IllegalArgumentException if the given player is null
   */
  int getScore(PlayerEnum.Player player) throws IllegalArgumentException;

  /**
   * Returns the current player.
   *
   * @return the current player
   */
  PlayerEnum.Player getCurrentPlayer();

  /**
   * Returns whether the game is over.
   *
   * @return whether the game is over
   */
  boolean isGameOver();

  /**
   * Attempts to place a piece of the current player's color at the given row and column.
   *
   * @param row the row at which to place the piece
   * @param col the column at which to place the piece
   * @throws IllegalArgumentException if the given row or column is out of bounds, or if the
   */
  void placePiece(int row, int col) throws IllegalArgumentException;

}

