package strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.Color;
import model.HexagonCell;
import model.ICell;
import model.ROModel;

public class AvoidEdgesTests {
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
    this.strategy = new AvoidEdges(testColor);
    this.mockModel = new MockModel(4, log);
  }

  @Test
  public void testAvoidEdgesWithNoFilter() {
    this.init();
    List<Integer> expected = List.of(-1, -1, 2);
    List<Integer> actual = this.strategy.chooseMove(this.mockModel
            , new ArrayList<>()).get(0).getCoordinates();
    Assert.assertEquals(expected, actual);
    System.out.println(this.strategy.chooseMove(this.mockModel
            , new ArrayList<>()).get(0).getCoordinates());
  }

  @Test
  public void testAvoidEdgesWithFilterSingleMove() {
    // Test that the strategy returns the uppermost-leftmost move when a single cell is the
    // filtered strategy move.
    this.init();
    List<Integer> expected = List.of(1, 1, -2);
    List<Integer> actual = this.strategy.chooseMove(this.mockModel
            , List.of(new HexagonCell(1, 1, -2))).get(0).getCoordinates();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testAvoidEdgesWithFilterTwoMovesNotEdgesDoesntChangeFilteredList() {
    // Test that the strategy returns the uppermost-leftmost move when multiple cells are the
    // filtered strategy moves.
    this.init();
    List<ICell> filteredList = List.of(new HexagonCell(1, 1, -2)
            , new HexagonCell(0, 1, -1));
    List<ICell> actual = this.strategy.chooseMove(this.mockModel, filteredList);
    Assert.assertEquals(filteredList, actual);
  }

  @Test
  public void testAvoidEdgesRemovesOptionOnEdge() {
    // Test that the strategy returns the uppermost-leftmost move when multiple cells are the
    // filtered strategy moves.
    this.init();
    List<ICell> filteredList = List.of(new HexagonCell(1, -3, 2)
            , new HexagonCell(0, 1, -1), new HexagonCell(1, 1, -2));
    List<ICell> actual = this.strategy.chooseMove(this.mockModel, filteredList);
    System.out.println(actual.get(0).getCoordinates());
    List<ICell> resultFilteredList = List.of(new HexagonCell(0, 1, -1)
            , new HexagonCell(1, 1, -2));
    Assert.assertEquals(resultFilteredList, actual);
  }

  @Test
  public void testAvoidEdgesKeepsEdgeMoveIfOnlyMoveIsEdgeMoves() {
    // Test that the strategy returns the uppermost-leftmost move when multiple cells are the
    // filtered strategy moves.
    this.init();
    List<ICell> filteredList = List.of(new HexagonCell(1, -3, 2));
    List<ICell> actual = this.strategy.chooseMove(this.mockModel, filteredList);
    Assert.assertEquals(filteredList, actual);
  }

  @Test
  public void testAvoidEdgesKeepsEdgeMoveIfOnlyMovesAreEdgeMoves() {
    // Test that the strategy returns the uppermost-leftmost move when multiple cells are the
    // filtered strategy moves.
    this.init();
    List<ICell> filteredList = List.of(new HexagonCell(1, -3, 2)
            , new HexagonCell(-1, -2, 3));
    List<ICell> actual = this.strategy.chooseMove(this.mockModel, filteredList);
    Assert.assertEquals(filteredList, actual);
  }
}