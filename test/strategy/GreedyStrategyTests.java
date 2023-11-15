package strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.HexagonBoard;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.strategy.GreedyStrat;
import cs3500.reversi.strategy.Strategy;

/**
 * This class tests the Greedy Strategy.
 */
public class GreedyStrategyTests {
  private ROModel mockModel;
  private Strategy strategy;
  private StringBuilder log;

  @Before
  public void init() {
    this.log = new StringBuilder();
    Color testColor = Color.BLACK;
    List<ICell> mockFilteredMoves = List.of();
    this.strategy = new GreedyStrat(testColor);
    this.mockModel = new MockModel(3, log);
  }

  // helper to initialize the model for integration tests
  private void createModel(IBoard board, int sideLength, StringBuilder log) {
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
  public void testBasicGreedyWhenAllMovesAreEqual() {
    // Test that the strategy returns the uppermost-leftmost moves when all valid moves are
    // equally beneficial.
    this.init();
    List<Integer> expected = List.of(-1, -1, 2);
    List<Integer> observed = this.strategy.chooseMove(this.mockModel
            , new ArrayList<>()).get(0).getCoordinates();
    Assert.assertEquals(expected, observed);
  }

  @Test
  public void testGreedyFiltersThroughPosibleMovesWhenEmptyListIsPassedToIt() {
    // Test if the greedy strategy goes through each of the possible moves if no moves are given to
    // it.
    this.init();
    List<Integer> expected = List.of(-1, -1, 2);
    List<Integer> observed = this.strategy.chooseMove(this.mockModel
            , new ArrayList<>()).get(0).getCoordinates();
    Assert.assertEquals(expected, observed);
    Assert.assertTrue(log.toString().contains("getValidMoves called with color: B"));
  }

  @Test
  public void testGreedyWhenOnlyOneMoveIsFiltered() {
    // Test that the strategy returns the single move when only one move is filtered.
    this.init();
    List<Integer> expected = List.of(1, 1, -2);
    List<Integer> observed = this.strategy.chooseMove(this.mockModel,
            List.of(new HexagonCell(1, 1, -2))).get(0).getCoordinates();
    Assert.assertEquals(expected, observed);
  }

  @Test
  public void testGreedyChoosesUpperLeftMostWhenMultipleMovesAreEqual() {
    this.init();
    List<Integer> expected = List.of(-1, -1, 2);
    List<Integer> observed = this.strategy.chooseMove(this.mockModel,
            List.of(new HexagonCell(1, 1, -2), new HexagonCell(-2, 1, 1),
                    new HexagonCell(-1, -1, 2))).get(0).getCoordinates();
    Assert.assertEquals(expected, observed);
  }

  @Test
  public void testGreedyChoosesBestMoveMultipleMoves() {
    this.init();
    this.strategy = new GreedyStrat(Color.WHITE);
    IBoard board = this.initBoard(6);
    this.addBasicStartingMoves(board);
    board.validMove(new HexagonCell(-2, 1, 1), Color.BLACK, true);
    this.createModel(board, 6, log);
    List<Integer> expected = List.of(1, 1, -2);
    List<Integer> observed = this.strategy.chooseMove(this.mockModel,
            //these are all the possible moves for white in this situation
            List.of(new HexagonCell(1, 1, -2), new HexagonCell(2, -1, -1),
                    new HexagonCell(-3, 1, 2), new HexagonCell(-1, -1, 2)))
            .get(0).getCoordinates();
    Assert.assertEquals(expected, observed);
    System.out.println(log);
  }
}
