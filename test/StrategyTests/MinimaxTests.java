package StrategyTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Color;
import model.HexagonBoard;
import model.HexagonCell;
import model.HexagonReversi;
import model.IBoard;
import model.ICell;
import model.IReversiModel;
import model.ROModel;
import strategy.GreedyStrat;
import strategy.MiniMaxStrategy;
import strategy.MiniMaxStrategyV2;
import strategy.Strategy;

public class MinimaxTests {
  private ROModel mockModel;
  private Strategy strategy;
  private List<ICell> mockFilteredMoves;
  private Color testColor;
  private StringBuilder log;
  private IReversiModel model;
  private List<ICell> moves;

  @Before
  public void init() {
    this.log = new StringBuilder();
    this.testColor = Color.BLACK;
    this.mockFilteredMoves = List.of();
    this.strategy = new MiniMaxStrategyV2(testColor);
    this.mockModel = new MockModel(3, log);
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

//  @Test
//  public void testMinimaxMinimizesOpponentsBestMove() {
//    this.init();
//
//    System.out.println(this.strategy.);
//  }

  @Test
  public void testMinimaxMinimizesOpponentsBestMove() {
    this.testColor = Color.BLACK;
    this.moves = List.of();
    this.strategy = new MiniMaxStrategyV2(testColor);
    this.model = new HexagonReversi(3);
    List<ICell> validMoves = model.getValidMoves(testColor);
    List<ICell> chosenMoves = this.strategy.chooseMove(model, validMoves);
    System.out.println(this.model.toString());
    model.placeCurrentPlayerPiece(chosenMoves.get(0));
    Assert.assertEquals(chosenMoves.get(0), new HexagonCell(-1,-1,2));
    System.out.println(this.model.toString());
    System.out.println(chosenMoves.get(0).getCoordinates());
  }



  @Test
  public void testScenarioWhereMinimaxChoosesAnyMoveFromSameMoves() {
    this.testColor = Color.BLACK;
    this.moves = List.of();
    this.strategy = new MiniMaxStrategyV2(testColor);
    this.model = new HexagonReversi(6);
    List<ICell> validMoves = model.getValidMoves(testColor);
    List<ICell> chosenMoves = this.strategy.chooseMove(model, validMoves);

    System.out.println(this.model.toString());
    model.placeCurrentPlayerPiece(chosenMoves.get(0));
    Assert.assertEquals(chosenMoves.get(0), new HexagonCell(-1,-1,2));

    System.out.println(this.model.toString());
    System.out.println(chosenMoves.get(0).getCoordinates());
  }

  @Test
  public void testBasicGreedyWhenAllMovesAreEqual() {
    this.testColor = Color.BLACK;
    this.moves = List.of();
    this.strategy = new MiniMaxStrategy(testColor);
    this.model = new HexagonReversi(3);
    List<ICell> validMoves = model.getValidMoves(testColor);
    List<ICell> chosenMoves = this.strategy.chooseMove(model, validMoves);
    System.out.println(this.model.toString());
    model.placeCurrentPlayerPiece(chosenMoves.get(0));
    Assert.assertEquals(chosenMoves.get(0), new HexagonCell(-1,-1,2));
    System.out.println(this.model.toString());
    System.out.println(chosenMoves.get(0).getCoordinates());
  }

  @Test
  public void testGreedyWhenOnlyOneMoveIsFiltered() {
    // Test that the strategy returns the single move when only one move is filtered.
    this.init();

  }

  @Test
  public void testGreedyChoosesUpperLeftMostWhenMultipleMovesAreEqual() {
    this.init();

  }

  @Test
  public void testGreedyChoosesBestMoveMultipleMoves() {
    this.init();

  }
}
