package model;
import java.util.Optional;

public interface IBoard {
  /**
   * Adds a cell to the board.
   * @param cell the cell to be added.
   * @param player the player to be added.
   */
  public void newCellOwner(ICell cell, Optional<Player> player);

  /**
   * Gets the state of the cell, meaning who owns it currently, or if it is empty.
   *
   * @param cell the cell to get the state of.
   */
  public Optional<Player> getCellState(ICell cell);

  /**
   * Returns whether a move is valid or not.
   */
  public boolean validMove(ICell cell, Player player) throws IllegalArgumentException;


  /**
   * Renders the board as a string.
   * @return  Rendered string for the board.
   */
  public String toString();
}