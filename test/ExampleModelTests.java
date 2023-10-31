import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import model.HexagonBoard;
import model.HexagonCell;
import model.HexagonReversi;
import model.HumanPlayer;
import model.IBoard;
import model.IPlayer;
import model.IReversiModel;
import model.Color;


/**
 * This class contains tests for the HexagonBoard class.d
 */
public class ExampleModelTests {
  IReversiModel basicModelThree;
  IReversiModel basicModelFour;
  IBoard riggedBoard;

  IPlayer blackPlayer;
  IPlayer whitePlayer;


  private void init() {
    basicModelThree = new HexagonReversi(3);
    basicModelFour = new HexagonReversi(4);

    riggedBoard = new HexagonBoard(3);
    riggedBoard.newCellOwner(new HexagonCell(0,0,0), Optional.empty());
    riggedBoard.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Color.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Color.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(Color.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(Color.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Color.BLACK));
    riggedBoard.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Color.BLACK));

    riggedBoard.newCellOwner(new HexagonCell(-2, 2, 0), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(-1, 2, -1), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(0, 2, -2), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(-2, 1, 1), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(-2, 0 , 2), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(-1, -1, 2), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(1, 1, -2), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(2, 0, -2), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(2, -1, -1), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(0, -2, 2), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(1, -2, 1), Optional.of(Color.WHITE));
    riggedBoard.newCellOwner(new HexagonCell(2, -2, 0), Optional.of(Color.WHITE));

    this.blackPlayer = new HumanPlayer(Color.BLACK);
    this.whitePlayer = new HumanPlayer(Color.WHITE);
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
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(0,0,0)),
            Optional.empty());
  }

  @Test
  public void testGetCellStateNonEmptyCell() {
    this.init();
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(-1, 1, 0)),
            Optional.of(Color.BLACK));
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(1, -1, 0)),
            Optional.of(Color.WHITE));
    Assert.assertEquals(this.basicModelThree.getCellState(new HexagonCell(0, -1, 1)),
            Optional.of(Color.BLACK));
  }

  @Test
  public void testGetScore() {
    this.init();
    // Before a move.
    Assert.assertEquals(this.basicModelThree.getScore(Color.WHITE),3);
    Assert.assertEquals(this.basicModelThree.getScore(Color.BLACK), 3);
    this.basicModelThree.placePiece(new HexagonCell(-1, -1, 2), this.blackPlayer);
    // After a move.
    Assert.assertEquals(this.basicModelThree.getScore(Color.WHITE),2);
    Assert.assertEquals(this.basicModelThree.getScore(Color.BLACK), 5);
  }

  @Test
  public void testGetCurrentPlayer() {
    this.init();
    // Initially before a move
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), Color.BLACK);
    this.basicModelThree.passTurn();
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), Color.WHITE);
  }

  @Test
  public void testGameOverDueToOnePlayerWinning() {
    this.init();
    this.basicModelThree.placePiece(new HexagonCell(-1, -1, 2), this.blackPlayer);
    this.basicModelThree.placePiece(new HexagonCell(-1, 2, -1), this.blackPlayer);
    this.basicModelThree.placePiece(new HexagonCell(1, -2, 1), this.blackPlayer);
    // Game over and Player.BLACK wins.
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test
  public void testGameOverDueToBoardFillingUp() {
    this.init();
    this.basicModelThree = new HexagonReversi(riggedBoard, 3);
    Assert.assertFalse(this.basicModelThree.isGameOver());
    this.basicModelThree.placePiece(new HexagonCell(0,0,0), this.whitePlayer);
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test
  public void testGameOverDueToNoMoreValidMovesAndBoardNotFilled() {
    this.init();
    this.basicModelThree.placePiece(new HexagonCell(-1, -1, 2), this.blackPlayer);
    this.basicModelThree.placePiece(new HexagonCell(-1, 2, -1), this.blackPlayer);
    this.basicModelThree.placePiece(new HexagonCell(1, -2, 1), this.blackPlayer);
    // Board is not full and there are no more valid moves left for both players.
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test
  public void testGameOverDueToBothPlayersPassing() {
    this.init();
    this.basicModelThree.passTurn();
    this.basicModelThree.passTurn();
    // Both players passed and the game is over.
    Assert.assertTrue(this.basicModelThree.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testCanOnlyAddToEmptyCell() {
    // Tests adding to a cell that is occupied by a non-empty player.
    this.init();
    this.basicModelThree.placePiece(new HexagonCell(-1, 1, 0), this.blackPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanOnlyAddToCellInBounds() {
    // Tests adding to a cell that is occupied by a non-empty player.
    this.init();
    this.basicModelThree.placePiece(new HexagonCell(-3, 3, 0), this.blackPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanOnlyAddToCellWithSumOfCoordinatesZero() {
    // Tests adding to a cell that is occupied by a non-empty player.
    this.init();
    this.basicModelThree.placePiece(new HexagonCell(-1, 1, 1), this.blackPlayer);
  }

  @Test
  public void testValidPassTurnChangesCurrentPlayer() {
    this.init();
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), Color.BLACK);
    this.basicModelThree.passTurn();
    Assert.assertEquals(this.basicModelThree.getCurrentColor(), Color.WHITE);
  }

  @Test(expected = IllegalStateException.class)
  public void testPassTurnThrowsExceptionIfGameIsOver() {
    this.init();
    this.basicModelThree.placePiece(new HexagonCell(-1, -1, 2), this.blackPlayer);
    this.basicModelThree.placePiece(new HexagonCell(-1, 2, -1), this.blackPlayer);
    this.basicModelThree.placePiece(new HexagonCell(1, -2, 1), this.blackPlayer);
    // Game is over.
    Assert.assertTrue(this.basicModelThree.isGameOver());
    this.basicModelThree.passTurn();
  }


}
