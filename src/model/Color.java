package model;

/**
 *
 */
public enum Color {
  BLACK, WHITE;

  /**
   * Returns the next player in the sequence.
   *
   * @return next player in sequence
   */
  public Color next() {
    return values()[(this.ordinal() + 1) % values().length];
  }

  public String toString() {
    if (this.equals(BLACK)) {
      return "B";
    } else {
      return "W";
    }
  }
}

