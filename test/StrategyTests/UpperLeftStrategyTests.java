package StrategyTests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.Color;
import model.HexagonBoard;
import model.HexagonCell;
import model.IBoard;
import model.ICell;
import model.ROModel;
import strategy.Strategy;
import strategy.UpperLeftStrat;

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
  }

  private void createModel(IBoard board, int sideLength, StringBuilder log) {
    this.mockModel = new MockModel(board, sideLength, log);
  }

  // Tests for UpperLeftStrat.
  @Test
  public void testBasicUpperLeftStratWithNoFilter() {
    // Test that the strategy returns the uppermost-leftmost move when no filtered
    // moves are passed to it.
    this.init();
    this.createModel(new HexagonBoard(3), 3, log);
    this.strategy.chooseMove(this.mockModel, new ArrayList<>());
    System.out.println(log);
  }

  @Test
  public void testUpperLeftStratWithFilterSingleMove() {
    // Test that the strategy returns the uppermost-leftmost move when a single cell is the
    // filtered strategy move.
    this.init();
    this.createModel(new HexagonBoard(3), 3, log);
    System.out.println(this.strategy.chooseMove(this.mockModel, List.of(new HexagonCell(1, 1, -2))).get(0).getCoordinates());
    System.out.println(log);
  }

  @Test
  public void testUpperLeftStratWithFilterTwoMoves() {
    // Test that the strategy returns the uppermost-leftmost move when multiple cells are the
    // filtered strategy moves.
    this.init();
    this.createModel(new HexagonBoard(3), 3, log);
    System.out.println(this.strategy.chooseMove(this.mockModel, List.of(new HexagonCell(1, 1, -2), new HexagonCell(0, 1, -1))).get(0).getCoordinates());
    System.out.println(log);
  }


}
