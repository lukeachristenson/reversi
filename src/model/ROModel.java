package model;

import java.util.List;
import java.util.Objects;

public interface ROModel {
  //TODO: Implement Read Only Model Interface
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
  boolean isGameOver();
  Color getCurrentColor();
  int getDimensions();
  List<ICell> getValidMoves(Color color);
}
