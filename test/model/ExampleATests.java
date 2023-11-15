package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.HexagonBoard;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.strategy.GreedyStrat;


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
    Map<ICell, Optional<Color>> mapCopy = this.hexagonBoardThree.getPositionsMapCopy();
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
    Assert.assertNotSame(copyBoard, hexagonBoardThree);
  }

  @Test
  public void testStrategy() {
    // Write a test for the basic strategy using the basic model.
    // This test should check that the basic strategy returns the correct move for the basic model.
    IReversiModel model = new HexagonReversi(6);

//    System.out.println(new UpperLeftStrat(Color.BLACK, model).chooseMove(model, List.of()).get(0).getCoordinates());
    System.out.println(new GreedyStrat(Color.BLACK).chooseMove(model, List.of()).get(0).getCoordinates());
  }
}
