package model;

/**
 * A class to represent a human player.
 */
public class HumanPlayer implements IPlayer {
  private final Color color;

  /**
   * Constructor for a HumanPlayer. Takes in a color and makes a player who will play that color.
   * @param color the color of the player.
   */
  public HumanPlayer(Color color) {
    this.color = color;
  }

  @Override
  public Color getColor() {
    if (this.color.toString().equals("B")) {
      return Color.BLACK;
    } else {
      return Color.WHITE;
    }
  }
}
