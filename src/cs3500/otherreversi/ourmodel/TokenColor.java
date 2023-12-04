package cs3500.otherreversi.ourmodel;

/**
 * An Enum for different colors to be placed by players.
 */
public enum TokenColor {
  BLACK, WHITE;

  /**
   * Returns the next player in the sequence.
   *
   * @return next player in sequence
   */
  public TokenColor next() {
    return values()[(this.ordinal() + 1) % values().length];
  }

  /**
   * Converts the color to a string.
   *
   * @return
   */
  public String toString() {
    if (this.equals(BLACK)) {
      return "B";
    } else {
      return "W";
    }
  }
}


