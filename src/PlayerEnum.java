public class PlayerEnum {
  public enum Player {
    BLACK, WHITE;

    /**
     * Returns the next player in the sequence.
     * @return next player in sequence
     */
    public Player next() {
      return values()[(this.ordinal() + 1) % values().length];
    }
  }
}

