package cs3500.reversi.model;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.player.IPlayer;

/**
 * This interface represents a read-only model for a game of Reversi.
 */
public interface ROModel {


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
   * @param tokenColor the color to get the score of.
   * @return the score of the given color.
   * @throws IllegalStateException if the game has not yet started.
   */
  int getScore(TokenColor tokenColor) throws IllegalStateException;

  /**
   * Returns the current color.
   *
   * @return the current color.
   */
  TokenColor getCurrentColor();

  /**
   * Returns the dimensions of the board.
   *
   * @return the dimensions of the board.
   */
  int getDimensions();

  /**
   * Returns the cell at the given coordinates.
   *
   * @param tokenColor the number of valid moves for this color.
   * @return the cell at the given coordinates.
   */
  List<ICell> getValidMoves(TokenColor tokenColor);

  /**
   * Returns the number of cells flipped by the given move.
   *
   * @param cell  the cell to make the move at.
   * @param tokenColor the color to make the move with.
   * @return the number of cells flipped by the given move.
   */
  int cellsFlipped(ICell cell, TokenColor tokenColor);

  /**
   * Returns the winner of the game.
   *
   * @return the winner of the game.
   */
  Optional<TokenColor> getWinner();

  /**
   * Returns whether the given move is valid.
   *
   * @param cell the cell to make the move at.
   * @return whether the given move is valid.
   */
  boolean validMove(ICell cell) throws IllegalArgumentException;
}
