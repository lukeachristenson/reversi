package StrategyTests;

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
import strategy.GreedyStrat;
import strategy.Strategy;

public class GreedyStrategyTests {
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
    this.strategy = new GreedyStrat(testColor);
    this.mockModel = new MockModel(3, log);
  }

  protected void createModel(IBoard board, int sideLength, StringBuilder log) {
    this.mockModel = new MockModel(board, sideLength, log);
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

    System.out.println(this.strategy.chooseMove(this.mockModel, new ArrayList<>()).get(0).getCoordinates());
    System.out.println(log);
  }

  @Test
  public void testGreedyWhenOnlyOneMoveIsFiltered() {
    // Test that the strategy returns the single move when only one move is filtered.
    this.init();

    System.out.println(this.strategy.chooseMove(this.mockModel, List.of(new HexagonCell(1, 1, -2))));
    System.out.println(log);
  }

  @Test
  public void testGreedyChoosesUpperLeftMostWhenMultipleMovesAreEqual() {
    this.init();

    System.out.println(this.strategy.chooseMove(this.mockModel,
            List.of(new HexagonCell(1, 1, -2), new HexagonCell(-2, 1, 1),
                    new HexagonCell(-1, -1, 2))).get(0).getCoordinates());
    System.out.println(log);
  }

  @Test
  public void testGreedyChoosesBestMoveMultipleMoves() {
    this.init();
    this.strategy = new GreedyStrat(Color.WHITE);
    IBoard board = new HexagonBoard(6);
    this.addBasicStartingMoves(board);
    board.validMove(new HexagonCell(-2, 1, 1), Color.BLACK, true);
    this.createModel(board, 6, log);


    System.out.println(this.strategy.chooseMove(this.mockModel,
            List.of()).get(0).getCoordinates());
    System.out.println(log);
  }
}
