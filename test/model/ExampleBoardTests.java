package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.HexagonBoard;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.IBoard;


/**
 * This class contains tests for the HexagonBoard class.d
 */
public class ExampleBoardTests {

  IBoard hexagonBoardThree;

  private void init() {
    this.hexagonBoardThree = new HexagonBoard(3);
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

  // TESTS FOR NEWCELLOWNER
  @Test
  public void testAddingToCellWithoutOwner() {
    this.init();
    this.hexagonBoardThree.newCellOwner(new HexagonCell(0, 0, 0), Optional.of(Color.BLACK));
    Assert.assertEquals(hexagonBoardThree.getCellOccupant(new HexagonCell(0, 0, 0)),
            Optional.of(Color.BLACK));
  }

  @Test
  public void testAddingToCellWithOwner() {
    this.init();
    this.hexagonBoardThree.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Color.WHITE));
    Assert.assertEquals(hexagonBoardThree.getCellOccupant(new HexagonCell(-1, 0, 1)),
            Optional.of(Color.WHITE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingOutOfBounds() {
    this.init();
    this.hexagonBoardThree.newCellOwner(new HexagonCell(-5, 0, 5),
            Optional.of(Color.WHITE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingNullCell() {
    this.init();
    this.hexagonBoardThree.newCellOwner(null,
            Optional.of(Color.WHITE));
  }


  // TESTS FOR GETCELLOCCUPANT
  @Test
  public void testGetEmptyCell() {
    this.init();
    Assert.assertTrue(this.hexagonBoardThree.getCellOccupant(new HexagonCell(0, 0, 0)).isEmpty());
  }

  @Test
  public void testGetNonEmptyCell() {
    this.init();
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new
            HexagonCell(-1, 0, 1)), Optional.of(Color.BLACK));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new
            HexagonCell(-2, 0, 2)), Optional.of(Color.WHITE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOutOfBounds() {
    this.init();
    this.hexagonBoardThree.getCellOccupant(new HexagonCell(-3, 0, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNullCellState() {
    this.init();
    this.hexagonBoardThree.getCellOccupant(null);
  }


  // TESTS FOR VALIDMOVE
  @Test(expected = IllegalArgumentException.class)
  public void testValidMoveOutOfBounds() {
    this.init();
    this.hexagonBoardThree.validMove(new HexagonCell(-3, 0, 3), Color.WHITE, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidMoveNullCell() {
    this.init();
    this.hexagonBoardThree.validMove(null, Color.WHITE, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testValidMoveOccupiedCellThrowsException() {
    this.init();
    this.hexagonBoardThree.validMove(new HexagonCell(-1, 0, 1), Color.WHITE, false);
  }

  @Test
  public void testValidMoveWithFlippingBool() {
    this.init();
    Assert.assertTrue(this.hexagonBoardThree.validMove(new HexagonCell(0, 0, 0),
            Color.WHITE, true));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(1, -1, 0)), Optional.of(Color.WHITE));
  }

  @Test
  public void testValidMoveWithoutFlippingBool() {
    this.init();
    Assert.assertTrue(this.hexagonBoardThree.validMove(new HexagonCell(0, 0, 0),
            Color.WHITE, false));
  }

  @Test
  public void testInvalidMoveWithFlippingBool() {
    this.init();
    Assert.assertFalse(this.hexagonBoardThree.validMove(new HexagonCell(0, 0, 0),
            Color.BLACK, true));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(1, -1, 0)), Optional.of(Color.BLACK));
  }

  @Test
  public void testInValidMoveWithoutFlippingBool() {
    this.init();
    Assert.assertFalse(this.hexagonBoardThree.validMove(new HexagonCell(0, 0, 0),
            Color.BLACK, false));
  }

  @Test
  public void testValidMoveWithFlippingBoolFlipsInAllDirections() {
    this.init();
    Assert.assertTrue(this.hexagonBoardThree.validMove(new HexagonCell(0, 0, 0),
            Color.WHITE, true));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(-1, 1, 0)), Optional.of(Color.WHITE));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(1, -1, 0)), Optional.of(Color.WHITE));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(0, -1, 1)), Optional.of(Color.WHITE));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(0, 1, -1)), Optional.of(Color.WHITE));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(1, 0, -1)), Optional.of(Color.WHITE));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(-1, 0, 1)), Optional.of(Color.WHITE));
  }

  @Test
  public void testValidMoveWithFlippingBoolFlipsInSomeDirections() {
    this.init();
    hexagonBoardThree.newCellOwner(new HexagonCell(-1, -1, 2), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(1, 1, -2), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(2, 0, -2), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(2, -1, -1), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(0, -2, 2), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(1, -2, 1), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(2, -2, 0), Optional.of(Color.BLACK));

    Assert.assertTrue(this.hexagonBoardThree.validMove(new HexagonCell(0, 0, 0),
            Color.WHITE, true));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(-1, 1, 0)), Optional.of(Color.WHITE));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(1, -1, 0)), Optional.of(Color.BLACK));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(0, -1, 1)), Optional.of(Color.BLACK));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(0, 1, -1)), Optional.of(Color.WHITE));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(1, 0, -1)), Optional.of(Color.BLACK));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(-1, 0, 1)), Optional.of(Color.WHITE));
  }

  @Test
  public void testValidMoveWithFlippingBoolFlipsInOneDirection() {
    this.init();
    hexagonBoardThree.newCellOwner(new HexagonCell(-1, -1, 2), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(1, 1, -2), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(2, 0, -2), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(-2, 2, 0), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(2, -1, -1), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(0, -2, 2), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(0, 2, -2), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(1, -2, 1), Optional.of(Color.BLACK));
    hexagonBoardThree.newCellOwner(new HexagonCell(2, -2, 0), Optional.of(Color.BLACK));

    Assert.assertTrue(this.hexagonBoardThree.validMove(new HexagonCell(0, 0, 0),
            Color.WHITE, true));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(-1, 1, 0)), Optional.of(Color.BLACK));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(1, -1, 0)), Optional.of(Color.BLACK));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(0, -1, 1)), Optional.of(Color.BLACK));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(0, 1, -1)), Optional.of(Color.BLACK));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(1, 0, -1)), Optional.of(Color.BLACK));
    Assert.assertEquals(this.hexagonBoardThree.getCellOccupant(new HexagonCell(-1, 0, 1)), Optional.of(Color.WHITE));
  }

  @Test
  public void testToString() {
    this.init();
    Assert.assertTrue(this.hexagonBoardThree.toString().contains(
            "       W W W \n" +
                    "      W B B W \n" +
                    "     W B - B W \n" +
                    "      W B B W   \n" +
                    "       W W W     \n"));
  }

  @Test
  public void testGetScore() {
    this.init();
    Assert.assertEquals(this.hexagonBoardThree.getColorCount(Color.WHITE), 12);
    Assert.assertEquals(this.hexagonBoardThree.getColorCount(Color.BLACK), 6);
    this.hexagonBoardThree.validMove(new HexagonCell(0, 0, 0), Color.WHITE, true);
    // White score is 18 and not 19 because the white token is not placed at the cell.
    Assert.assertEquals(this.hexagonBoardThree.getColorCount(Color.WHITE), 18);
    Assert.assertEquals(this.hexagonBoardThree.getColorCount(Color.BLACK), 0);
  }

  @Test
  public void testValidMovesLeft() {
    this.init();
    Assert.assertEquals(this.hexagonBoardThree.validMovesLeft(Color.BLACK).size(), 0);
    Assert.assertEquals(this.hexagonBoardThree.validMovesLeft(Color.WHITE),
            List.of(new HexagonCell(0, 0, 0)));
    this.hexagonBoardThree.validMove(new HexagonCell(0, 0, 0), Color.WHITE, true);
    Assert.assertEquals(this.hexagonBoardThree.validMovesLeft(Color.BLACK).size(), 0);
    Assert.assertEquals(this.hexagonBoardThree.validMovesLeft(Color.WHITE).size(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBoardLessThanThreeRingsThrows() {
    IBoard board = new HexagonBoard(2);
  }
}
