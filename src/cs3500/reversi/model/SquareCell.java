package cs3500.reversi.model;

import java.util.List;

/**
 * Represents a cell within a reversi game with a square board.
 */
public class SquareCell implements ICell{
  private final int row;
  private final int col;

  /**
   * Constructor for a SquareCell. Takes in a row and column.
   *
   * @param row the row of the cell.
   * @param col the column of the cell.
   */
  public SquareCell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public List<Integer> getCoordinates() {
    return List.of(this.row, this.col);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof SquareCell)) {
      return false;
    }
    SquareCell that = (SquareCell) other;
    return this.row == that.row && this.col == that.col;
  }

  @Override
  public int hashCode() {
    return this.row * 31 + this.col;
  }

  @Override
  public String toString() {
    return "(" + this.row + ", " + this.col + ")";
  }

}
