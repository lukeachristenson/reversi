package StrategyTests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import model.Color;
import model.HexagonBoard;
import model.HexagonCell;
import model.HexagonReversi;
import model.IBoard;
import model.ICell;
import model.IReversiModel;
import model.ROModel;
import strategy.Strategy;
import strategy.UpperLeftStrat;
import view.BasicReversiView;
import view.ReversiTextView;
import view.ReversiView;

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

  @Test
  public void testBasicUpperLeftStratWithNoFilter() {
    // Test that the strategy returns the uppermost-leftmost moves when no filtered
    // moves are passed to it.
    this.init();

    System.out.println(this.strategy.chooseMove(this.mockModel, new ArrayList<>()).get(0).getCoordinates());
    System.out.println(log);
  }

  @Test
  public void testUpperLeftStratWithFilterSingleMove() {
    // Test that the strategy returns the uppermost-leftmost move when a single cell is the
    // filtered strategy move.
    this.init();
    System.out.println(this.strategy.chooseMove(this.mockModel, List.of(new HexagonCell(1, 1, -2))).get(0).getCoordinates());
    System.out.println(log);
  }

  @Test
  public void testUpperLeftStratWithFilterTwoMoves() {
    // Test that the strategy returns the uppermost-leftmost move when multiple cells are the
    // filtered strategy moves.
    this.init();
    System.out.println(this.strategy.chooseMove(this.mockModel, List.of(new HexagonCell(1, 1, -2), new HexagonCell(0, 1, -1))).get(0).getCoordinates());
    System.out.println(log);
  }
}
