package view;

/**
 * A marker interface for all text-based views to be used in the Reversi game.
 */
public interface TextView {

  /**
   * Returns a rendered string that displays the game's state.
   *
   * @return String  Rendered string for the game state.
   */
  String toString();

}
