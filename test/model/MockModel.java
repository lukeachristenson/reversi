package model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.HexagonBoard;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.model.ROModel;

/**
 * This class represents a game of HexagonReversi. It implements the IReversiModel interface.
 * HexReversi is a hexagonal version of the game Reversi.
 */
public class MockModel implements ROModel {
  private final IBoard board;
  private final int sideLength;
  private final StringBuilder log;

  /**
   * Constructor for a HexagonReversi model. Takes in a board, current player, pass count, and
   * whether the game has started or not.
   *
   * @param sideLength the side length of the board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public MockModel(int sideLength, StringBuilder log) throws IllegalArgumentException {
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    } else {
      this.sideLength = sideLength;
    }

    this.log = log;
    IPlayer currentPlayer = new HumanPlayer(TokenColor.BLACK);
    // rings excluding the center cell = sideLength - 1
    this.board = this.initBoard(sideLength - 1);
    this.addStartingMoves();
  }

  /**
   * Constructor for a HexagonReversi model used ONLY for testing. Rigs the game by
   * passing in a hexagonal board for the game.
   *
   * @param hexBoard rigged board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public MockModel(IBoard hexBoard, int sideLength, StringBuilder log)
          throws IllegalArgumentException {
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    } else {
      this.sideLength = sideLength;
    }
    this.log = log;
    this.board = hexBoard;
  }

  //helper to initialize a board based on sideLength
  private IBoard initBoard(int rings) throws IllegalStateException {
    // rings + 1 = sideLength, includes the center ring here
    IBoard hexReturn = new HexagonBoard(rings + 1);
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
  private void addStartingMoves() {

    this.board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(TokenColor.BLACK));
    this.board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(TokenColor.WHITE));
    this.board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(TokenColor.BLACK));
    this.board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(TokenColor.WHITE));
    this.board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(TokenColor.BLACK));
    this.board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(TokenColor.WHITE));
  }


  @Override
  public int getDimensions() throws IllegalStateException {
    log.append("getDimensions called\n");
    return this.sideLength;
  }

  @Override
  public int getScore(TokenColor tokenColor) throws IllegalStateException {
    log.append("getScore called\n");
    return this.board.getColorCount(tokenColor);
  }

  @Override
  public TokenColor getCurrentColor() throws IllegalStateException {
    log.append("getCurrentColor called\n");
    return TokenColor.BLACK;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    log.append("isGameOver called\n");
    return false;
  }


  @Override
  public String toString() {
    log.append("toString called\n");
    return this.board.toString();
  }


  @Override
  public List<ICell> getValidMoves(TokenColor tokenColor) throws IllegalStateException {
    log.append("getValidMoves called with color: " + tokenColor.toString() + "\n");
    return this.board.validMovesLeft(tokenColor);
  }

  @Override
  public int cellsFlipped(ICell cell, TokenColor tokenColor) {
    log.append("cellsFlipped called with cell: " + cell.getCoordinates()
            + " and color: " + tokenColor.toString());
    int initialScore = this.board.getColorCount(tokenColor);
    int finalScore = 0;
    if (this.board.validMove(cell, tokenColor, false)) {
      IBoard boardCopy = this.createBoardCopy();
      boardCopy.validMove(cell, tokenColor, true);
      finalScore = boardCopy.getColorCount(tokenColor);
    }
    log.append("  ---------Advantage: " + (finalScore - initialScore) + "\n");
    return finalScore - initialScore;
  }

  @Override
  public Optional<TokenColor> getWinner() {
    return Optional.of(TokenColor.BLACK);
  }

  @Override
  public boolean validMove(ICell cell) throws IllegalArgumentException {
    log.append("validMove called\n");
    return false;
  }

  @Override
  public IBoard createBoardCopy() {
    Map<ICell, Optional<TokenColor>> mapCopy = this.board.getPositionsMapCopy();
    IBoard copyBoard = new HexagonBoard(this.sideLength);

    for (ICell cell : mapCopy.keySet()) {
      copyBoard.newCellOwner(cell, mapCopy.get(cell));
    }
    return copyBoard;
  }
}
