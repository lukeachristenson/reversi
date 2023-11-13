import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Color;
import model.HexagonBoard;
import model.HexagonReversi;
import model.IBoard;
import model.ICell;

public class MockBoard extends HexagonBoard {

  /**
   * Constructor for a HexagonBoard. Takes in a side length for the board.
   *
   * @param numRings the number of rings of hexagons apart from the center one in the board.
   */
  public MockBoard(int numRings) {
    super(numRings);
  }

  @Override
  public void newCellOwner(ICell cell, Optional<Color> player) throws IllegalArgumentException {

  }

  @Override
  public Optional<Color> getCellOccupant(ICell cell) throws IllegalArgumentException {
    return Optional.empty();
  }

  @Override
  public boolean validMove(ICell cell, Color color, boolean flip) throws IllegalArgumentException, IllegalStateException {
    return false;
  }

  @Override
  public int getColorCount(Color color) {
    return 0;
  }

  @Override
  public List<ICell> validMovesLeft(Color colorToAdd) {
    return null;
  }

  @Override
  public Map<ICell, Optional<Color>> getPositionsMapCopy() {
    return null;
  }

  @Override
  public int getNumRings() {
    return 0;
  }

}
