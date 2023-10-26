import java.util.List;

public interface ICell {
  /**
   * Returns the location of this cell.
   */
  List<Integer> getCoordinates();

  /**
   * Returns the player that owns this cell.
   *
   * @return the player that owns this cell
   */
  PlayerEnum.Player getPlayer();

  /**
   * Returns whether this cell is empty.
   *
   * @return whether this cell is empty
   */
  boolean isEmpty();

  /**
   * Returns whether this cell is black.
   *
   * @return whether this cell is black
   */
  boolean isBlack();

  /**
   * Returns whether this cell is white.
   *
   * @return whether this cell is white
   */
  boolean isWhite();

  /**
   * Returns whether this cell is the given player's.
   *
   * @param player the player to check
   * @return whether this cell is the given player's
   * @throws IllegalArgumentException if the given player is null
   */
  boolean isPlayer(PlayerEnum.Player player) throws IllegalArgumentException;

  /**
   * Returns whether this cell is the opposite of the given player's.
   *
   * @param player the player to check
   * @return whether this cell is the opposite of the given player's
   * @throws IllegalArgumentException if the given player is null
   */
  boolean isOppositePlayer(PlayerEnum.Player player) throws IllegalArgumentException;

  /**
   * Returns whether this cell is the same color as the given cell.
   *
   * @param cell the cell to
   */
  boolean isSameColor(ICell cell);
}
