package cs3500.reversi.model;

import java.util.List;
import java.util.Optional;

public interface ROModel {
  /**
   * Returns the current player.
   *
   * @return The current player.
   * @throws IllegalStateException if the game has not yet sta
   */
  IPlayer getCurrentPlayer() throws IllegalStateException;

  /**
   * Returns a copy of the board.
   *
   * @return a copy of the board.
   */
  IBoard createBoardCopy();

  /**
   * Returns whether the game is over.
   *
   * @return whether the game is over.
   */
  boolean isGameOver();

  /**
   * Returns the score of the given color.
   *
   * @param color the color to get the score of.
   * @return the score of the given color.
   * @throws IllegalStateException
   */
  int getScore(Color color) throws IllegalStateException;

  /**
   * Returns the current color.
   *
   * @return the current color.
   */
  Color getCurrentColor();

  /**
   * Returns the dimensions of the board.
   *
   * @return the dimensions of the board.
   */
  int getDimensions();

  /**
   * Returns the cell at the given coordinates.
   *
   * @param color the number of valid moves for this color.
   * @return the cell at the given coordinates.
   */
  List<ICell> getValidMoves(Color color);

  /**
   * Returns the number of cells flipped by the given move.
   *
   * @param cell  the cell to make the move at.
   * @param color the color to make the move with.
   * @return the number of cells flipped by the given move.
   */
  int cellsFlipped(ICell cell, Color color);

  /**
   * Returns the winner of the game.
   *
   * @return the winner of the game.
   */
  Optional<Color> getWinner();

  /**
   * Returns whether the given move is valid.
   *
   * @param cell the cell to make the move at.
   * @return whether the given move is valid.
   */
  boolean validMove(ICell cell) throws IllegalArgumentException;
}
