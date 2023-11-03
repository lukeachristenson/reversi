import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import model.HexagonBoard;
import model.HexagonCell;
import model.HexagonReversi;
import model.IBoard;
import model.Color;
import model.ICell;
import model.IReversiModel;


/**
 * This class contains tests for the HexagonBoard class.d
 */
public class ExampleATests {

  IBoard hexagonBoardThree;

  IReversiModel reversiModel;

  private void init() {
    this.hexagonBoardThree = new HexagonBoard(5);
    {
      hexagonBoardThree.newCellOwner(new HexagonCell(0, 0, 0), Optional.empty());
      hexagonBoardThree.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Color.BLACK));
      hexagonBoardThree.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Color.BLACK));
      hexagonBoardThree.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(Color.BLACK));
      hexagonBoardThree.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(Color.BLACK));
      hexagonBoardThree.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Color.BLACK));
      hexagonBoardThree.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Color.BLACK));

      hexagonBoardThree.newCellOwner(new HexagonCell(-2, 2, 0), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(-1, 2, -1), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(0, 2, -2), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(-2, 1, 1), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(-2, 0, 2), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(-1, -1, 2), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(1, 1, -2), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(2, 0, -2), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(2, -1, -1), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(0, -2, 2), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(1, -2, 1), Optional.of(Color.WHITE));
      hexagonBoardThree.newCellOwner(new HexagonCell(2, -2, 0), Optional.of(Color.WHITE));
    }

    reversiModel = new HexagonReversi(hexagonBoardThree, 6);
  }

  @Test
  public void testIfBoardMapDeeoCopyWorks() {
    this.init();
    Map< ICell, Optional<Color>> mapCopy = this.hexagonBoardThree.getPositionsMapCopy();
    for (ICell cell : mapCopy.keySet()) {
      Assert.assertEquals(mapCopy.get(cell), this.hexagonBoardThree.getCellOccupant(cell));
    }
    // Tested deep copy only with the temporary method getBoardPositions().
    // System.out.println(mapCopy == this.hexagonBoardThree.getBoardPositions());
  }

  @Test
  public void testIfReversiCreateBoardCopyWorks() {
    // Write a test to see if the IReversiModel createBoardCopy() method works.
    // You should test that the board returned by createBoardCopy() is equal to the board
    // that createBoardCopy() was called on.
    this.init();
    IBoard copyBoard = reversiModel.createBoardCopy();
    Assert.assertFalse(copyBoard == hexagonBoardThree);
  }
}
