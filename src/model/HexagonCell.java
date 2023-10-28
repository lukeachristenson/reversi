package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cell within a reversi game with a hexagonal board.
 * Coordinate system used to represent a hexagonal board is cube coordinates.
 * Information about cube coordinate system
 * <a href="https://www.redblobgames.com/grids/hexagons/">here</a>
 */
public class HexagonCell implements ICell {
  private final int q;
  private final int r;
  private final int s;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HexagonCell that = (HexagonCell) o;
    return q == that.q && r == that.r && s == that.s;
  }

  @Override
  public int hashCode() {
    return Objects.hash(q, r, s);
  }

  public HexagonCell(int q, int r, int s) {
    // Throw an exception if the sum of the coordinates is not zero.
    if(q + r + s != 0) {
      throw new IllegalArgumentException("Sum of coordinates is not zero.");
    }
    this.q = q;
    this.r = r;
    this.s = s;
  }

  @Override
  public List<Integer> getCoordinates() {
    return new ArrayList<>(List.of(this.q, this.r, this.s));
  }
}
