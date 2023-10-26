import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cell within a reversi game with a hexagonal board.
 * Coordinate system used to represent a hexagonal board is cube coordinates.
 * Information about cube coordinate system
 * <a href="https://www.redblobgames.com/grids/hexagons/">here</a>
 */
public class HexagonCell implements ICell {
  private final int x;
  private final int y;
  private final int z;

  public HexagonCell(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public List<Integer> getCoordinates() {
    return new ArrayList<>(List.of(this.x, this.y, this.z));
  }
}
