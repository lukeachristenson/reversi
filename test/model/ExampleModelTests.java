package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.HexagonBoard;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.model.IReversiModel;


/**
 * This class contains tests for the HexagonBoard class.d
 */
public class ExampleModelTests {
  IReversiModel basicModelThree;
  IReversiModel basicModelFour;
  IBoard riggedBoard;

  IPlayer blackPlayer;
  IPlayer whitePlayer;

  // Initialize the model, players, and controller
  private void init() {
    basicModelThree = new HexagonReversi(3);
    basicModelFour = new HexagonReversi(4);

    riggedBoard = new HexagonBoard(3);
    riggedBoard.newCellOwner(new HexagonCell(0, 0, 0), Optional.empty());
    riggedBoard.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(TokenColor.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(TokenColor.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(TokenColor.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(TokenColor.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(TokenColor.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(TokenColor.BLACK));

    riggedBoard.newCellOwner(new HexagonCell(-2, 2, 0), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(-1, 2, -1), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(0, 2, -2), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(-2, 1, 1), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(-2, 0, 2), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(-1, -1, 2), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(1, 1, -2), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(2, 0, -2), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(2, -1, -1), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(0, -2, 2), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(1, -2, 1), Optional.of(TokenColor.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(2, -2, 0), Optional.of(TokenColor.WHITE));

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
    basicModelThree = new HexagonReversi(2);
  }

  @Test
  public void testGetCellStateEmptyCell() {
    this.init();
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(0, -2, 2)),
            Optional.empty());
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(-1, -1, 2)),
            Optional.empty());
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(0, 0, 0)),
            Optional.empty());
  }

  @Test
  public void testGetCellStateNonEmptyCell() {
    this.init();
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(-1, 1, 0)),
            Optional.of(TokenColor.BLACK));
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(1, -1, 0)),
            Optional.of(TokenColor.WHITE));
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(0, -1, 1)),
            Optional.of(TokenColor.BLACK));
  }

  @Test
  public void testGetScore() {
    this.init();
    // Before a move.
    Assert.assertEquals(this.basicModelThree.getScore(TokenColor.WHITE), 3);
    Assert.assertEquals(this.basicModelThree.getScore(TokenColor.BLACK), 3);
    //fixme this might not work
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-1, -1, 2));
    // After a move.
    Assert.assertEquals(this.basicModelThree.getScore(TokenColor.WHITE), 2);
    Assert.assertEquals(this.basicModelThree.getScore(TokenColor.BLACK), 5);
  }

  @Test
  public void testGetCurrentPlayer() {
    this.init();
    // Initially before a move
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), TokenColor.BLACK);
    this.basicModelThree.passTurn(true);
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), TokenColor.WHITE);
  }

  @Test
  public void testGameOverDueToOnePlayerWinning() {
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-1, -1, 2));
    this.basicModelThree.passTurn(false);
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-1, 2, -1));
    this.basicModelThree.passTurn(false);
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(1, -2, 1));
    // Game over and Player.BLACK wins.
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test
  public void testGameOverDueToBoardFillingUp() {
    this.init();
    this.basicModelThree = new HexagonReversi(riggedBoard, 3);
    Assert.assertFalse(this.basicModelThree.isGameOver());
    //to switch to white player
    this.basicModelThree.passTurn(false);
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(0, 0, 0));
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test
  public void testGameOverDueToNoMoreValidMovesAndBoardNotFilled() {
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-1, -1, 2));
    this.basicModelThree.passTurn(false);
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-1, 2, -1));
    this.basicModelThree.passTurn(false);
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(1, -2, 1));
    // Board is not full and there are no more valid moves left for both players.
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test
  public void testGameOverDueToBothPlayersPassing() {
    this.init();
    this.basicModelThree.passTurn(true);
    this.basicModelThree.passTurn(true);
    // Both players passed and the game is over.
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testCanOnlyAddToEmptyCell() {
    // Tests adding to a cell that is occupied by a non-empty player.
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-1, 1, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanOnlyAddToCellInBounds() {
    // Tests adding to a cell that is occupied by a non-empty player.
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-3, 3, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanOnlyAddToCellWithSumOfCoordinatesZero() {
    // Tests adding to a cell that is occupied by a non-empty player.
    this.init();
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-1, 1, 1));
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
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-1, -1, 2));
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(-1, 2, -1));
    this.basicModelThree.placeCurrentPlayerPiece(new HexagonCell(1, -2, 1));
    // Game is over.
    Assert.assertTrue(this.basicModelThree.isGameOver());
    this.basicModelThree.passTurn(true);
  }


  // New tests
  @Test
  public void testPlaceCurrentPlayerPiece() {
    // Tests if the placeCurrentPlayerPiece method works.
    this.init();
    basicModelFour = new HexagonReversi(4);
    basicModelFour.placeCurrentPlayerPiece(new HexagonCell(-1, -1, 2));
    basicModelFour.placeCurrentPlayerPiece(new HexagonCell(-2, 1, 1));
    Assert.assertEquals(basicModelFour.getCellState(new HexagonCell(-1, -1, 2)),
            Optional.of(TokenColor.BLACK));
  }



}
