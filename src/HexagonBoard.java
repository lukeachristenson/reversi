import java.util.HashMap;
import java.util.Optional;

public class HexagonBoard implements IBoard {
  private final HashMap<ICell, Optional<Player>> boardPositions;
  private final int sideLength;

  public HexagonBoard(int sideLength) {
    this.boardPositions = new HashMap<>();
    this.sideLength = sideLength;
  }

  @Override
  public void newCellOwner(ICell cell, Optional<Player> player) {
    this.boardPositions.put(cell, player);
  }

  @Override
  public Optional<Player> getCellState(ICell hexagonCell) throws IllegalArgumentException{
    return this.boardPositions.get(hexagonCell);
  }

  @Override
  public boolean validMove(ICell cell, Player player) {
    for (int i = 0; i < cell.getCoordinates().size(); i++) {
      if (cell.getCoordinates().get(i) < -sideLength || cell.getCoordinates().get(i) > sideLength) {
        throw new IllegalArgumentException();
      }
    }
    //todo this is where move logic will come into play
    return false;
  }
}
