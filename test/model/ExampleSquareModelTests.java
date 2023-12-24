package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.SquareBoard;
import cs3500.reversi.model.SquareCell;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.model.IReversiModel;

/**
 * This class contains tests for the SquareBoard class.
 */
public class ExampleSquareModelTests {
  IReversiModel basicModelThree;
  IReversiModel basicModelFour;
  IBoard riggedBoard;

  IPlayer blackPlayer;
  IPlayer whitePlayer;

  // Initialize the model, players, and controller
  private void init() {
    basicModelThree = new SquareReversi(3);
    basicModelFour = new SquareReversi(4);

    // Standard Reversi setup for a 3x3 board
    riggedBoard = new SquareBoard(3);
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        riggedBoard.newCellOwner(new SquareCell(row, col), Optional.empty());
      }
    }
    riggedBoard.newCellOwner(new SquareCell(1, 1), Optional.of(TokenColor.BLACK));
    riggedBoard.newCellOwner(new SquareCell(1, 2), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new SquareCell(2, 1), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new SquareCell(2, 2), Optional.of(TokenColor.BLACK));

    this.blackPlayer = new HumanPlayer(TokenColor.BLACK);
    this.whitePlayer = new HumanPlayer(TokenColor.WHITE);
  }

  @Test
  public void testGetDimensions() {
    this.init();
    Assert.assertEquals(this.basicModelThree.getDimensions(), 3);
    Assert.assertEquals(this.basicModelFour.getDimensions(), 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGameConfig() {
    this.init();
    basicModelThree = new SquareReversi(2); // Assuming the minimum board size is larger than 2
  }

  @Test
  public void testGetCellStateEmptyCell() {
    this.init();
    Assert.assertEquals(this.basicModelThree.getCellState(new SquareCell(0, 0)),
            Optional.empty());
    Assert.assertEquals(this.basicModelThree.getCellState(new SquareCell(1, 1)),
            Optional.empty());
    Assert.assertEquals(this.basicModelThree.getCellState(new SquareCell(2, 2)),
            Optional.empty());
  }

  @Test
  public void testGetCellStateNonEmptyCell() {
    this.init();
    Assert.assertEquals(this.basicModelThree.getCellState(new SquareCell(1, 1)),
            Optional.of(TokenColor.BLACK));
    Assert.assertEquals(this.basicModelThree.getCellState(new SquareCell(1, 2)),
            Optional.of(TokenColor.WHITE));
    Assert.assertEquals(this.basicModelThree.getCellState(new SquareCell(2, 1)),
            Optional.of(TokenColor.WHITE));
  }

  @Test
  public void testGetScore() {
    this.init();
    // Before a move.
    Assert.assertEquals(this.basicModelThree.getScore(TokenColor.WHITE), 2);
    Assert.assertEquals(this.basicModelThree.getScore(TokenColor.BLACK), 2);
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(2, 3));
    // After a move.
    Assert.assertEquals(this.basicModelThree.getScore(TokenColor.WHITE), 4); // Example value
    Assert.assertEquals(this.basicModelThree.getScore(TokenColor.BLACK), 1); // Example value
  }

  @Test
  public void testGetCurrentPlayer() {
    this.init();
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), TokenColor.BLACK);
    this.basicModelThree.passTurn(true);
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), TokenColor.WHITE);
  }

  @Test
  public void testGameOverDueToOnePlayerWinning() {
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(0, 1));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(0, 0));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(1, 0));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(2, 0));
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test
  public void testGameOverDueToBoardFillingUp() {
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(0, 1));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(0, 2));
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test
  public void testGameOverDueToNoMoreValidMovesAndBoardNotFilled() {
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(0, 1));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(0, 2));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(1, 0));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(1, 1));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(2, 1));
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }


  @Test
  public void testGameOverDueToBothPlayersPassing() {
    this.init();
    this.basicModelThree.passTurn(true);
    this.basicModelThree.passTurn(true);
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testCanOnlyAddToEmptyCell() {
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanOnlyAddToCellInBounds() {
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(4, 4)); // Out of bounds
  }

  @Test
  public void testValidPassTurnChangesCurrentPlayer() {
    this.init();
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), TokenColor.BLACK);
    this.basicModelThree.passTurn(true);
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), TokenColor.WHITE);
  }

  @Test(expected = IllegalStateException.class)
  public void testPassTurnThrowsExceptionIfGameIsOver() {
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(0, 1));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(1, 1));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(2, 1));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(0, 2));
    this.basicModelThree.placeCurrentPlayerPiece(new SquareCell(1, 2));
    Assert.assertTrue(this.basicModelThree.isGameOver());

    this.basicModelThree.passTurn(true);
  }


  @Test
  public void testPlaceCurrentPlayerPiece() {
    this.init();
    basicModelFour = new SquareReversi(4);
    basicModelFour.placeCurrentPlayerPiece(new SquareCell(2, 2));
    basicModelFour.placeCurrentPlayerPiece(new SquareCell(2, 3));
    Assert.assertEquals(basicModelFour.getCellState(new SquareCell(2, 2)), Optional.of(TokenColor.BLACK));
  }


}
