package StrategyTests;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Color;
import model.HexagonBoard;
import model.HexagonCell;
import model.HumanPlayer;
import model.IBoard;
import model.ICell;
import model.IPlayer;
import model.ROModel;

/**
 * This class represents a game of HexReversi. It implements the IReversiModel interface.
 * HexReversi is a hexagonal version of the game Reversi.
 */
public class MockModel  implements ROModel {
  private final IBoard board;
  private IPlayer currentPlayer;
  private final int sideLength;
  private final StringBuilder log;
//  private final List<ICell>

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
    this.currentPlayer = new HumanPlayer(Color.BLACK);
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
  public MockModel(IBoard hexBoard, int sideLength, StringBuilder log) throws IllegalArgumentException {
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

    this.board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(Color.BLACK));
    this.board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Color.WHITE));
    this.board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Color.BLACK));
    this.board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Color.WHITE));
    this.board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Color.BLACK));
    this.board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(Color.WHITE));
  }


  @Override
  public int getDimensions() throws IllegalStateException {
    log.append("getDimensions called\n");
    return this.sideLength;
  }

  @Override
  public int getScore(Color color) throws IllegalStateException {
    log.append("getScore called\n");
    return this.board.getColorCount(color);
  }

  @Override
  public Color getCurrentColor() throws IllegalStateException {
    log.append("getCurrentColor called\n");
    return Color.BLACK;
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
  public IPlayer getCurrentPlayer() {
    log.append("getCurrentPlayer called\n");
    return new HumanPlayer(this.currentPlayer.getColor());
  }

  @Override
  public List<ICell> getValidMoves(Color color) throws IllegalStateException {
    log.append("getValidMoves called with color: " + color.toString() + "\n");
    return this.board.validMovesLeft(color);
  }

  @Override
  public int cellsFlipped(ICell cell, Color color) {
    log.append("cellsFlipped called with cell: " + cell.getCoordinates() + " and color: " + color.toString() + "\n");
    int initialScore = this.board.getColorCount(color);
    int finalScore = 0;
    if(this.board.validMove(cell, color, false)) {
      IBoard boardCopy = this.createBoardCopy();
      boardCopy.validMove(cell, color, true);
      finalScore = boardCopy.getColorCount(color);
    }

    return finalScore - initialScore;
  }

  @Override
  public boolean validMove(ICell cell) throws IllegalArgumentException {
    log.append("validMove called\n");
    return false;
  }

  @Override
  public IBoard createBoardCopy() {
    Map<ICell, Optional<Color>> mapCopy = this.board.getPositionsMapCopy();
    IBoard copyBoard = new HexagonBoard(this.sideLength);

    for (ICell cell : mapCopy.keySet()) {
      copyBoard.newCellOwner(cell, mapCopy.get(cell));
    }
    return copyBoard;
  }
}
