package cs3500.reversi.model;

import java.util.Optional;

import cs3500.reversi.controller.ModelFeatures;

/**
 * This interface represents a model for a game of Reversi.
 */
public interface IReversiModel extends ROModel {

  /**
   * Returns the dimensions of the board.
   *
   * @return the dimensions of the board as an integer.
   * @throws IllegalStateException if the game has not yet started
   */
  int getDimensions() throws IllegalStateException;

  /**
   * Returns the cell at the given row and column.
   *
   * @return the cell at the given row and column.
   * @throws IllegalStateException if the game has not yet started.
   */
  Optional<TokenColor> getCellState(ICell cell) throws IllegalStateException;

  /**
   * Returns the current score of the given player.
   *
   * @param tokenColor the player whose score to return
   * @return the current score of the given player
   * @throws IllegalStateException if the game has not yet started
   */
  int getScore(TokenColor tokenColor) throws IllegalStateException;

  /**
   * Returns the current color for actions made in the model like placing a piece.
   *
   * @return the current color of the player whose turn it is.
   * @throws IllegalStateException if the game has not yet started.
   */
  TokenColor getCurrentColor() throws IllegalStateException;

  /**
   * Returns whether the game is over.
   *
   * @return whether the game is over.
   * @throws IllegalStateException if the game has not yet started.
   */
  boolean isGameOver() throws IllegalStateException;

  /**
   * Places the current player on the given cell.
   *
   * @param targetCell the cell to place the current player on.
   * @throws IllegalStateException    if the game has not yet started.
   * @throws IllegalArgumentException if the target cell is null.
   */
  void placeCurrentPlayerPiece(ICell targetCell) throws IllegalStateException
          , IllegalArgumentException;

  /**
   * action to pass ones turn, deferring the turn to the other player. if all players pass,
   * the game is over.
   *
   * @throws IllegalStateException if the game has not yet started.
   */
  void passTurn(boolean increment) throws IllegalStateException;


  /**
   * Returns the rendered string of the game model.
   *
   * @return Rendered string of the game model.
   * @throws IllegalStateException if the game has not yet started.
   */
  String toString() throws IllegalStateException;


  boolean validMove(ICell cell) throws IllegalArgumentException;

  void addListener(ModelFeatures listener);

  void startGame();
}

