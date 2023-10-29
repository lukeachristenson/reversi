

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.util.Optional;

import model.HexagonBoard;
import model.HexagonCell;
import model.HexagonReversi;
import model.IReversiModel;
import model.Player;
import view.ReversiTextView;
import view.TextView;


/**
 * This class contains tests for the HexagonBoard class.d
 */
public class ExampleBoardTests {

  /**
   *    Tests for HexagonBoard:
   *      Tests for newCellOwner
   *        testAddingToCellWithoutOwner
   *        testAddingToCellWithOwner
   *        testAddingOutOfBounds
   *        testAddingNullCell
   *
   *    Tests for getCellState:
   *      testGetEmptyCell
   *      testGetNonEmptyCell
   *      testGetOutOfBounds
   *      testGetNullCellState
   *
   *    Tests for validMove:
   *      testValidMoveOutOfBounds
   *      testValidMoveNullCell
   *      testValidMoveOccupiedCell
   *      testValidMoveWithFlippingBool
   *      testValidMoveWithoutFlippingBool
   *      testInvalidMoveWithoutFlippingBool
   *      testInvalidMoveWithFlippingBool
   *      testValidMoveWithFlippingBoolFlipsInAllDirections
   *      testValidMoveWithFlippingBoolFlipsInSomeDirections
   *      testValidMoveWithFlippingBoolFlipsInOneDirection
   *
   *
   *    Tests for toString:
   *      testToString
   *    Tests for getScore
   *    Tests for validMovesLeft
   *
   *    Tests for HexagonModel:
   *      Tests for getDimensions
   *      Tests for getCellState
   *      Tests for getScore
   *      Tests for getCurrentPlayer:
   *        testEmptyPlayerReturned
   *        testCorrectNonEmptyPlayerReturned
   *
   *      Tests for isGameOver:
   *        testGameOverDueToOnePlayerWinning
   *        testGameOverDueToBoardFillingUp
   *        testGameOverDueToNoMoreValidMovesAndBoardNotFilled
   *        testGameOverDueToBothPlayersPassing
   *
   *
   *      Tests for placePiece:
   *       testCanOnlyAddToEmptyCell
   *       testCanOnlyAddToCellInBounds
   *       testCanOnlyAddToCellWithSumOfCoordinatesZero
   *       testPlacePieceThrowsExceptionIfGameIsNotRunning
   *      Tests for passTurn:
   *       testPassTurn
   *       testPassTurnThrowsExceptionIfGameIsOver
   *       testPassTurnThrowsExceptionIfGameIsNotRunning
   *
   *
   *    Tests for ReversiTextView
   *      testReturnsEmptyStringIfGameIsOver
   *      testTextViewWorks
   *
   *
   *    Tests for Cell:
   *      test Q+R+S = 0  getCoordinates returns the valid string
   *      test Q+R+S != 0 throws IllegalArgumentException
   *
   *    Tests for Player:
   *      testNext
   *
   */





  @Test
  public void testHexagonBoardToString() {
    HexagonBoard hexagonBoard = new HexagonBoard(3);
    // Populate the board with some cells and players
//    hexagonBoard.newCellOwner(, Optional.empty());
    hexagonBoard.newCellOwner(new HexagonCell(0,0,0), Optional.empty());
    hexagonBoard.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(0, 1, -1), Optional.empty());
    hexagonBoard.newCellOwner(new HexagonCell(-1, 1, 0), Optional.empty());
    hexagonBoard.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(0, -1, 1), Optional.empty());

    hexagonBoard.newCellOwner(new HexagonCell(-2, 2, 0), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(-1, 2, -1), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(0, 2, -2), Optional.of(Player.WHITE));

    hexagonBoard.newCellOwner(new HexagonCell(-2, 1, 1), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(-2, 0 , 2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(-1, -1, 2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(1, 1, -2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(2, 0, -2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(2, -1, -1), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(0, -2, 2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(1, -2, 1), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(2, -2, 0), Optional.of(Player.WHITE));

    System.out.println(hexagonBoard.validMove(new HexagonCell(0, 0, 0), Player.BLACK, false));
    System.out.println(hexagonBoard.validMove(new HexagonCell(0, 0, 0), Player.WHITE, false));
    System.out.println(hexagonBoard.validMove(new HexagonCell(0, -1, 1), Player.WHITE, false));

    System.out.println(hexagonBoard.toString());
  }

  @Test
  public void test() {
    IReversiModel model = new HexagonReversi(6);
    TextView t1 = new ReversiTextView(model);
    System.out.println(t1.toString());
    System.out.println("Black Score: " + model.getScore(Player.BLACK));
    System.out.println("White Score: " + model.getScore(Player.WHITE));

    model.placePiece(new HexagonCell(-1, -1, 2), Player.BLACK);

    System.out.println(t1.toString());
    System.out.println("Black Score: " + model.getScore(Player.BLACK));
    System.out.println("White Score: " + model.getScore(Player.WHITE));
  }
}
