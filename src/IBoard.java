import java.util.Optional;

public interface IBoard {
  /**
   * Adds a cell to the board
   * @param cell
   * @param player
   */
  public void newCellOwner(ICell cell, Optional<Player> player);

  /**
   * Gets the state of the cell, meaning who owns it currently, or if it is empty.
   *
   * @param cell
   */
  public Optional<Player> getCellState(ICell cell);

  /**
   * Returns whether a move is valid or not.
   */
  public boolean validMove(ICell cell, Player player) throws IllegalArgumentException;
}
