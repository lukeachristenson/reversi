package model;

import java.util.List;
import java.util.Optional;

public interface IBoard {
  /**
   * Adds a cell to the board.
   *
   * @param cell   the cell to be added.
   * @param player the player to be added.
   */
  public void newCellOwner(ICell cell, Optional<Color> player);

  /**
   * Gets the state of the cell, meaning who owns it currently, or if it is empty.
   *
   * @param cell the cell to get the state of.
   */
  public Optional<Color> getCellOccupant(ICell cell);

  /**
   * Returns whether a move is valid or not.
   */
  public boolean validMove(ICell cell, Color color, boolean flip) throws IllegalArgumentException;


  /**
   * Renders the board as a string.
   *
   * @return Rendered string for the board.
   */
  public String toString();

  /**
   * Returns the score of this player in the board.
   *
   * @param color Player whose score is to be returned.
   * @return Score of the given player.
   */
  public int getScore(Color color);


  /**
   * Returns a list of valid moves left for the given player.
   *
   * @param colorToAdd The player for whom the list of valid moves left is to be returned.
   * @return List of valid moves for the player.
   */
  public List<ICell> validMovesLeft(Color colorToAdd);


}