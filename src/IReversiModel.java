import java.util.Optional;

/**
 * This interface represents a model for a game of Reversi.
 */
public interface IReversiModel {
  void startGame(int sideLength);

  /**
   * Returns the dimensions of the board which varies depending on the type of board.
   *
   * @return the dimensions of the board as a list of integers.
   */
  int getDimensions();

  /**
   * Returns the cell at the given row and column.
   *
   * @return the cell at the given row and column
   * @throws IllegalArgumentException if the given row or column is out of bounds
   */
  Optional<PlayerEnum.Player> getCellState(ICell cell) throws IllegalArgumentException;

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
   * Attempts to place a piece of the current player's color at the given Cell.
   *
   * @param targetCell the Cell at which to place the piece
   * @throws IllegalArgumentException if the given row or column is out of bounds, or if the
   */
  void placePiece(ICell targetCell, PlayerEnum.Player player) throws IllegalStateException;

}

