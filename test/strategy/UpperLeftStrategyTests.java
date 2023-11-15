package strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Color;
import model.HexagonBoard;
import model.HexagonCell;
import model.IBoard;
import model.ICell;
import model.ROModel;

public class UpperLeftStrategyTests {
  private ROModel mockModel;
  private Strategy strategy;
  private List<ICell> mockFilteredMoves;
  private Color testColor;
  private StringBuilder log;

  @Before
  public void init() {
    this.log = new StringBuilder();
    this.testColor = Color.BLACK;
    this.mockFilteredMoves = List.of();
    this.strategy = new UpperLeftStrat(testColor);
    this.mockModel = new MockModel(6, log);
  }

  protected void createModel(IBoard board, int sideLength, StringBuilder log) {
    this.mockModel = new MockModel(board, sideLength, log);
  }

  private IBoard initBoard(int sideLength) throws IllegalStateException {
    // rings + 1 = sideLength, includes the center ring here
    IBoard hexReturn = new HexagonBoard(sideLength);
    Integer rings = sideLength - 1;
    for (int q = -rings; q <= rings; q++) {
      int r1 = Math.max(-rings, -q - rings);
      int r2 = Math.min(rings, -q + rings);
      for (int r = r1; r <= r2; r++) {
        HexagonCell hp = new HexagonCell(q, r, -q - r);
        hexReturn.newCellOwner(hp, Optional.empty());
      }
    }
    return hexReturn;
  }

  //helper to add the starting moves of each player
  private void addBasicStartingMoves(IBoard board) {
    board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(Color.BLACK));
    board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Color.WHITE));
    board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Color.BLACK));
    board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Color.WHITE));
    board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Color.BLACK));
    board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(Color.WHITE));
  }

  @Test
  public void testBasicUpperLeftStratWithNoFilter() {
    // Test that the strategy returns the uppermost-leftmost moves when no filtered
    // moves are passed to it.
    this.init();
    List<Integer> expected = List.of(-1, -1, 2);
    List<Integer> actual = this.strategy.chooseMove(this.mockModel
            , new ArrayList<>()).get(0).getCoordinates();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testUpperLeftStratWithFilterSingleMove() {
    // Test that the strategy returns the uppermost-leftmost move when a single cell is the
    // filtered strategy move.
    this.init();
    List<Integer> expected = List.of(1, 1, -2);
    List<Integer> actual = this.strategy.chooseMove(this.mockModel
            , List.of(new HexagonCell(1, 1, -2))).get(0).getCoordinates();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testUpperLeftStratWithFilterTwoMoves() {
    // Test that the strategy returns the uppermost-leftmost move when multiple cells are the
    // filtered strategy moves.
    this.init();
    List<Integer> expected = List.of(-1, -1, 2);
    List<Integer> actual = this.strategy.chooseMove(this.mockModel
            , List.of(new HexagonCell(1, 1, -2)
                    , new HexagonCell(0, 1, -1))).get(0).getCoordinates();
    Assert.assertEquals(expected, actual);
  }
}
