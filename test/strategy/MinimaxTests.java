package strategy;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.HexagonBoard;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.strategy.MiniMaxStrategy;
import cs3500.reversi.strategy.Strategy;
import cs3500.reversi.strategy.OurAlgorithmStrat;
import model.MockModel;

/**
 * This class tests the Minimax Strategy.
 */
public class MinimaxTests {
  private ROModel mockModel;
  private Strategy strategy;
  private TokenColor testTokenColor;
  private StringBuilder log;
  private IReversiModel model;
  private List<ICell> moves;
  private Strategy whiteStrategy;

  private void init() {
    this.log = new StringBuilder();
    this.testTokenColor = TokenColor.BLACK;
    List<ICell> mockFilteredMoves = List.of();
    this.strategy = new OurAlgorithmStrat(testTokenColor);
    this.mockModel = new MockModel(3, log);
  }

  private void initForIntegrationTests() {
    this.testTokenColor = TokenColor.BLACK;
    this.moves = List.of();
    this.strategy = new MiniMaxStrategy(testTokenColor);
    this.whiteStrategy = new MiniMaxStrategy(TokenColor.WHITE);
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
    board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(TokenColor.BLACK));
    board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(TokenColor.WHITE));
    board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(TokenColor.BLACK));
    board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(TokenColor.WHITE));
    board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(TokenColor.BLACK));
    board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(TokenColor.WHITE));
  }

  @Test
  public void testMinimaxMinimizesOpponentsBestMove() {
    initForIntegrationTests();
    this.model = new HexagonReversi(3);
    List<ICell> validMoves = model.getValidMoves(testTokenColor);
    List<ICell> chosenMoves = this.strategy.chooseMove(model, validMoves);
    System.out.println(this.model.toString());
    model.placeCurrentPlayerPiece(chosenMoves.get(0));
    Assert.assertEquals(chosenMoves.get(0), new HexagonCell(-1, -1, 2));
    System.out.println(this.model.toString());
    System.out.println(chosenMoves.get(0).getCoordinates());
  }

  @Test
  public void testMinimaxMinimizesOpponentsBestMoveWithMock() {
    this.init();
    this.testTokenColor = TokenColor.BLACK;
    this.moves = List.of();
    this.strategy = new MiniMaxStrategy(testTokenColor);
    this.mockModel = new MockModel(3, log);
    IBoard boardCopy = this.mockModel.createBoardCopy();
    List<ICell> validMoves = boardCopy.validMovesLeft(testTokenColor);
    List<ICell> chosenMoves = this.strategy.chooseMove(mockModel, validMoves);
    boardCopy = this.mockModel.createBoardCopy();
    boardCopy.validMove(chosenMoves.get(0), testTokenColor, true);
    mockModel = new MockModel(boardCopy, 3, log);
    Assert.assertEquals(chosenMoves.get(0), new HexagonCell(-1, -1, 2));
    System.out.println(log);
  }

  @Test
  public void testMiniMaxReturnsPassWhenPlayerWinsNextMove() {
    // This test checks if the strategy returns a pass when the player wins the next move if you
    // have no choice but to play one of the opponent-win causing moves.
    // This test also checks if the strategy chooses the first winning move it encounters.
    initForIntegrationTests();
    this.model = new HexagonReversi(3);
    this.model.placeCurrentPlayerPiece(new HexagonCell(-2, 1, 1));
    this.model.placeCurrentPlayerPiece(new HexagonCell(-1, -1, 2));
    this.model.placeCurrentPlayerPiece(new HexagonCell(1, -2, 1));
    this.model.placeCurrentPlayerPiece(new HexagonCell(2, -1, -1));

    // This move causes the white to have a chance at winning in the next move.
    // this.model.placeCurrentPlayerPiece(new HexagonCell(1, 1, -2));
    // White winning move
    // this.model.placeCurrentPlayerPiece(new HexagonCell(-1, 2, -1));
    List<ICell> chosenMoves = this.strategy.chooseMove(model, List.of());
    System.out.println(chosenMoves.size()); // TODO Insert Assert here


    model.passTurn(false);
    this.model.placeCurrentPlayerPiece(new HexagonCell(-1, 2, -1));
    // Winning move:
    // this.model.placeCurrentPlayerPiece(new HexagonCell(1, 1, -2));
    chosenMoves = this.strategy.chooseMove(model, List.of());
    System.out.println(chosenMoves.get(0).getCoordinates()); // TODO Insert Assert here
    Assert.assertEquals(chosenMoves.get(0), new HexagonCell(1, 1, -2));
    model.placeCurrentPlayerPiece(new HexagonCell(1, 1, -2));
    System.out.println("Model winner evaluated in the test: " + model.getWinner());
  }

  @Test
  public void testScenarioWhereMinimaxChoosesRightMovesForBothColors() {
    initForIntegrationTests();
    this.model = new HexagonReversi(6);

    List<ICell> validMoves = model.getValidMoves(testTokenColor);
    List<ICell> chosenMoves = this.strategy.chooseMove(model, validMoves);
    model.placeCurrentPlayerPiece(chosenMoves.get(0));
    System.out.println(this.model.toString());
    Assert.assertEquals(chosenMoves.get(0), new HexagonCell(-1, -1, 2));

    List<ICell> validMove = model.getValidMoves(TokenColor.WHITE);
    List<ICell> chosenMove = this.whiteStrategy.chooseMove(model, new ArrayList<>());
    model.placeCurrentPlayerPiece(chosenMove.get(0));
    Assert.assertEquals(chosenMove.get(0), new HexagonCell(-2, -1, 3));
    System.out.println(this.model.toString());
  }
}
