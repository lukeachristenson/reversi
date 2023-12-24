package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.SquareBoard;
import cs3500.reversi.model.SquareCell;
import cs3500.reversi.model.IBoard;

/**
 * This class contains tests for the SquareBoard class.
 */
public class ExampleSquareBoardTests {

  IBoard squareBoard;

  private void init() {
    this.squareBoard = new SquareBoard(4); // Assuming a 4x4 board for simplicity
    // Initialize the board with a standard Reversi setup
    squareBoard.newCellOwner(new SquareCell(1, 1), Optional.of(TokenColor.BLACK));
    squareBoard.newCellOwner(new SquareCell(1, 2), Optional.of(TokenColor.WHITE));
    squareBoard.newCellOwner(new SquareCell(2, 1), Optional.of(TokenColor.WHITE));
    squareBoard.newCellOwner(new SquareCell(2, 2), Optional.of(TokenColor.BLACK));
  }

  // TESTS FOR NEWCELLOWNER
  @Test
  public void testAddingToCellWithoutOwner() {
    this.init();
    this.squareBoard.newCellOwner(new SquareCell(0, 0), Optional.of(TokenColor.BLACK));
    Assert.assertEquals(squareBoard.getCellOccupant(new SquareCell(0, 0)), Optional.of(TokenColor.BLACK));
  }

  @Test
  public void testAddingToCellWithOwner() {
    this.init();
    this.squareBoard.newCellOwner(new SquareCell(2, 1), Optional.of(TokenColor.WHITE));
    Assert.assertEquals(squareBoard.getCellOccupant(new SquareCell(2, 1)), Optional.of(TokenColor.WHITE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingOutOfBounds() {
    this.init();
    this.squareBoard.newCellOwner(new SquareCell(4, 4), Optional.of(TokenColor.WHITE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingNullCell() {
    this.init();
    this.squareBoard.newCellOwner(null, Optional.of(TokenColor.WHITE));
  }

  // TESTS FOR GETCELLOCCUPANT
  @Test
  public void testGetEmptyCell() {
    this.init();
    Assert.assertTrue(this.squareBoard.getCellOccupant(new SquareCell(0, 0)).isEmpty());
  }

  @Test
  public void testGetNonEmptyCell() {
    this.init();
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(1, 1)), Optional.of(TokenColor.BLACK));
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(2, 1)), Optional.of(TokenColor.WHITE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOutOfBounds() {
    this.init();
    this.squareBoard.getCellOccupant(new SquareCell(4, 4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNullCellState() {
    this.init();
    this.squareBoard.getCellOccupant(null);
  }

  // TESTS FOR VALIDMOVE
  @Test(expected = IllegalArgumentException.class)
  public void testValidMoveOutOfBounds() {
    this.init();
    this.squareBoard.validMove(new SquareCell(4, 4), TokenColor.WHITE, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidMoveNullCell() {
    this.init();
    this.squareBoard.validMove(null, TokenColor.WHITE, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testValidMoveOccupiedCellThrowsException() {
    this.init();
    this.squareBoard.validMove(new SquareCell(1, 1), TokenColor.WHITE, false);
  }

  @Test
  public void testValidMoveWithFlippingBool() {
    this.init();
    Assert.assertTrue(this.squareBoard.validMove(new SquareCell(2, 3), TokenColor.WHITE, true));
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(2, 2)), Optional.of(TokenColor.WHITE));
  }

  @Test
  public void testValidMoveWithoutFlippingBool() {
    this.init();
    Assert.assertTrue(this.squareBoard.validMove(new SquareCell(2, 3), TokenColor.WHITE, false));
  }

  @Test
  public void testInvalidMoveWithFlippingBool() {
    this.init();
    Assert.assertFalse(this.squareBoard.validMove(new SquareCell(0, 0), TokenColor.BLACK, true));
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(0, 0)), Optional.of(TokenColor.BLACK));
  }

  @Test
  public void testInValidMoveWithoutFlippingBool() {
    this.init();
    Assert.assertFalse(this.squareBoard.validMove(new SquareCell(0, 0), TokenColor.BLACK, false));
  }

  @Test
  public void testValidMoveWithFlippingBoolFlipsInAllDirections() {
    this.init();
    Assert.assertTrue(this.squareBoard.validMove(new SquareCell(3, 2), TokenColor.WHITE, true));
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(2, 1)), Optional.of(TokenColor.WHITE));
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(1, 2)), Optional.of(TokenColor.WHITE));
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(2, 2)), Optional.of(TokenColor.WHITE));
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(2, 3)), Optional.of(TokenColor.WHITE));
  }


  @Test
  public void testValidMoveWithFlippingBoolFlipsInSomeDirections() {
    this.init();
    squareBoard.newCellOwner(new SquareCell(0, 1), Optional.of(TokenColor.BLACK));
    squareBoard.newCellOwner(new SquareCell(1, 0), Optional.of(TokenColor.BLACK));
    squareBoard.newCellOwner(new SquareCell(1, 3), Optional.of(TokenColor.BLACK));
    squareBoard.newCellOwner(new SquareCell(2, 2), Optional.of(TokenColor.BLACK));
    squareBoard.newCellOwner(new SquareCell(3, 1), Optional.of(TokenColor.BLACK));

    Assert.assertTrue(this.squareBoard.validMove(new SquareCell(2, 3), TokenColor.WHITE, true));
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(1, 2)), Optional.of(TokenColor.WHITE));
  }

  @Test
  public void testValidMoveWithFlippingBoolFlipsInOneDirection() {
    this.init();
    squareBoard.newCellOwner(new SquareCell(0, 1), Optional.of(TokenColor.BLACK));
    squareBoard.newCellOwner(new SquareCell(1, 0), Optional.of(TokenColor.BLACK));
    squareBoard.newCellOwner(new SquareCell(3, 3), Optional.of(TokenColor.BLACK));

    Assert.assertTrue(this.squareBoard.validMove(new SquareCell(2, 3), TokenColor.WHITE, true));
    Assert.assertEquals(this.squareBoard.getCellOccupant(new SquareCell(1, 2)), Optional.of(TokenColor.BLACK));
  }

  @Test
  public void testToString() {
    this.init();
    String boardString = this.squareBoard.toString();
    String expectedString = "B W - -\n" +
            "W B - -\n" +
            "- - - -\n" +
            "- - - -\n";
    Assert.assertEquals(boardString, expectedString);
  }

  @Test
  public void testGetScore() {
    this.init();
    Assert.assertEquals(this.squareBoard.getColorCount(TokenColor.WHITE), 2);
    Assert.assertEquals(this.squareBoard.getColorCount(TokenColor.BLACK), 2);
    this.squareBoard.validMove(new SquareCell(2, 3), TokenColor.WHITE, true);
    // Update the expected score based on the move made
    Assert.assertEquals(this.squareBoard.getColorCount(TokenColor.WHITE), 4);
    Assert.assertEquals(this.squareBoard.getColorCount(TokenColor.BLACK), 1);
  }

  @Test
  public void testValidMovesLeft() {
    this.init();
    Assert.assertEquals(this.squareBoard.validMovesLeft(TokenColor.BLACK).size(), 0);
    Assert.assertEquals(this.squareBoard.validMovesLeft(TokenColor.WHITE).size(), 1);
    this.squareBoard.validMove(new SquareCell(2, 3), TokenColor.WHITE, true);
    Assert.assertEquals(this.squareBoard.validMovesLeft(TokenColor.BLACK).size(), 1);
    Assert.assertEquals(this.squareBoard.validMovesLeft(TokenColor.WHITE).size(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBoardLessThanMinimumSizeThrows() {
    IBoard board = new SquareBoard(1);
  }
}
