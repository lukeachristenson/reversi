package model;

import java.util.Optional;

/**
 * This interface represents a model for a game of Reversi.
 */
public interface IReversiModel {


  /**
   * Returns the dimensions of the board which varies depending on the type of board.
   *
   * @return the dimensions of the board as a list of integers.
   */
  public int getDimensions();

  /**
   * Returns the cell at the given row and column.
   *
   * @return the cell at the given row and column
   * @throws IllegalArgumentException if the given row or column is out of bounds
   */
  public Optional<Color> getCellState(ICell cell) throws IllegalArgumentException;

  /**
   * Returns the current score of the given player.
   *
   * @param color the player whose score to return
   * @return the current score of the given player
   * @throws IllegalArgumentException if the given player is null
   */
  public int getScore(Color color) throws IllegalArgumentException;

  /**
   * Returns the current score of the given player.
   *
   * @param player the player whose score to return
   * @return the current score of the given player
   * @throws IllegalArgumentException if the given player is null
   */
  public int getScore(IPlayer player) throws IllegalArgumentException;

  /**
   * Returns the current color.
   *
   * @return the current color
   */
  public Color getCurrentColor();

  /**
   * Returns whether the game is over.
   *
   * @return whether the game is over
   */
  public boolean isGameOver();

  /**
   * Attempts to place a piece of the current player's color at the given Cell.
   *
   * @param targetCell the Cell at which to place the piece
   * @throws IllegalArgumentException if the given row or column is out of bounds, or if the
   */
  public void placePiece(ICell targetCell, IPlayer player) throws IllegalStateException;

  /**
   * action to pass ones turn, deferring the turn to the other player. if all players pass,
   * the game is over.
   */
  public void passTurn() throws IllegalStateException;

  /**
   * Returns the number of consecutive passes that have been made in the game so far.
   *
   * @return number of consecutive passes that have been made in the game so far.
   */
  public int getPassCount();


  /**
   * Returns the rendered string of the game model.
   *
   * @return Rendered string of the game model.
   */
  String toString();


  /**
   * Returns the current player.
   *
   * @return The current player.
   */
  IPlayer getCurrentPlayer();
}

