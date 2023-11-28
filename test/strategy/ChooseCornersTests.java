package strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.strategy.ChooseCorners;
import cs3500.reversi.strategy.Strategy;
import model.MockModel;

/**
 * Tests for the ChooseCorners strategy.
 */
public class ChooseCornersTests {
  private ROModel mockModel;
  private Strategy strategy;

  @Before
  public void init() {
    StringBuilder log = new StringBuilder();
    TokenColor testTokenColor = TokenColor.BLACK;
    List<ICell> mockFilteredMoves = List.of();
    this.strategy = new ChooseCorners(testTokenColor);
    this.mockModel = new MockModel(4, log);
  }

  @Test
  public void testChooseCornersWithNoFilter() {
    this.init();
    List<Integer> expected = List.of(-1, -1, 2);
    List<Integer> actual = this.strategy.chooseMove(this.mockModel
            , new ArrayList<>()).get(0).getCoordinates();
    Assert.assertEquals(expected, actual);
    System.out.println(this.strategy.chooseMove(this.mockModel
            , new ArrayList<>()).get(0).getCoordinates());
  }

  @Test
  public void testChooseCornersWithFilterSingleMoveNotACorner() {
    this.init();
    List<Integer> expected = List.of(1, 1, -2);
    List<Integer> actual = this.strategy.chooseMove(this.mockModel
            , List.of(new HexagonCell(1, 1, -2))).get(0).getCoordinates();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testChooseCornersWithFilterTwoMovesNotCornersDoesntChangeFilteredList() {
    this.init();
    List<ICell> filteredList = List.of(new HexagonCell(1, 1, -2)
            , new HexagonCell(0, 1, -1));
    List<ICell> actual = this.strategy.chooseMove(this.mockModel, filteredList);
    Assert.assertEquals(filteredList, actual);
  }

  @Test
  public void testChooseCornersOnlyReturnsCornersIfPresent() {
    this.init();
    List<ICell> filteredList = List.of(new HexagonCell(1, -3, 2)
            , new HexagonCell(0, 1, -1), new HexagonCell(0, -3, 3));
    List<ICell> actual = this.strategy.chooseMove(this.mockModel, filteredList);
    System.out.println(actual.get(0).getCoordinates());
    List<ICell> resultFilteredList = List.of(new HexagonCell(0, -3, 3));
    Assert.assertEquals(resultFilteredList, actual);
  }

  @Test
  public void testChooseCornersReturnsMultipleCornersIfThereAre() {
    this.init();
    List<ICell> filteredList = List.of(new HexagonCell(1, -3, 2)
            , new HexagonCell(-1, -2, 3), new HexagonCell(0, -3, 3)
            , new HexagonCell(3, -3, 0));
    List<ICell> actual = this.strategy.chooseMove(this.mockModel, filteredList);
    List<ICell> resultFilteredList = List.of(new HexagonCell(0, -3, 3)
            , new HexagonCell(3, -3, 0));
    Assert.assertEquals(resultFilteredList, actual);
  }
}
