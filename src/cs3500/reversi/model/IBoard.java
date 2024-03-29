package cs3500.reversi.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This interface represents a board for a game of Reversi.
 */
public interface IBoard {
  /**
   * Adds a cell to the board or can reassign a cell to a player.
   *
   * @param cell   the cell to be added.
   * @param player the player to be added.
   * @throws IllegalArgumentException if the cell is null, or the coordinates are out of bounds.
   */
  void newCellOwner(ICell cell, Optional<TokenColor> player) throws IllegalArgumentException;

  /**
   * Gets the occupant of the cell, either a player or empty represented by an optional.
   *
   * @param cell the cell to get the state of.
   * @return the occupant of the cell.
   * @throws IllegalArgumentException if the cell is null, or the coordinates are out of bounds.
   */
  Optional<TokenColor> getCellOccupant(ICell cell) throws IllegalArgumentException;

  /**
   * Returns whether a move is valid or not. If the boolean flip is true, then the move is made and
   * all the cells that are supposed to be flipped based on the move are flipped.
   *
   * @param cell       the cell to be added.
   * @param tokenColor the color of the player.
   * @param flip       whether to flip the cells or not.
   * @return whether the move is valid or not.
   * @throws IllegalArgumentException if the cell is null or the coordinates are out of bounds.
   * @throws IllegalStateException    if the cell is already occupied.
   */
  boolean validMove(ICell cell, TokenColor tokenColor, boolean flip) throws IllegalArgumentException
          , IllegalStateException;


  /**
   * Renders the board as a string.
   *
   * @return Rendered string for the board.
   */
  String toString();

  /**
   * Returns the score of this player in the board.
   *
   * @param tokenColor Player whose score is to be returned.
   * @return Score of the given player.
   */
  int getColorCount(TokenColor tokenColor);


  /**
   * Returns a list of valid moves left for the given player.
   *
   * @param tokenColorToAdd The player for whom the list of valid moves left is to be returned.
   * @return List of valid moves for the player.
   */
  List<ICell> validMovesLeft(TokenColor tokenColorToAdd);


  /**
   * Returns a deep copy of the map of positions to colors of this board.
   *
   * @return Map of positions to colors.
   */
  Map<ICell, Optional<TokenColor>> getPositionsMapCopy();

  int getNumRings();

  int[][] getBoardDirections();
}