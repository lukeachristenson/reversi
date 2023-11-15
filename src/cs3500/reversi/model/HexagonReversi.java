package cs3500.reversi.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class represents a game of HexagonReversi. It implements the IReversiModel interface.
 * HexReversi is a hexagonal version of the game Reversi.
 */
public class HexagonReversi implements IReversiModel {
  private final IBoard board;
  private final int sideLength;
  private IPlayer currentPlayer;
  private final IPlayer player1;
  private final IPlayer player2;
  private int passCount;
  private boolean gameRunning;


  /**
   * Constructor for a HexagonReversi model. Takes in a board, current player, pass count, and
   * whether the game has started or not.
   *
   * @param sideLength the side length of the board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public HexagonReversi(int sideLength) throws IllegalArgumentException {
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    } else {
      this.sideLength = sideLength;
    }

    this.gameRunning = true;
    this.player1 = new HumanPlayer(Color.BLACK);
    this.player2 = new HumanPlayer(Color.WHITE);
    this.currentPlayer = this.player1;
    this.passCount = 0;
    this.board = this.initBoard(sideLength); // rings excluding the center cell = sideLength - 1
    this.addStartingMoves();
  }

  /**
   * Constructor for a HexagonReversi model used ONLY for testing. Rigs the game by
   * passing in a hexagonal board for the game.
   *
   * @param hexBoard rigged board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public HexagonReversi(IBoard hexBoard, int sideLength) throws IllegalArgumentException {
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    } else {
      this.sideLength = sideLength;
    }
    this.player1 = new HumanPlayer(Color.BLACK);
    this.player2 = new HumanPlayer(Color.WHITE);
    this.currentPlayer = this.player1;
    this.gameRunning = true;
    this.passCount = 0;
    this.board = hexBoard;
  }

  //helper to check if the game is started before running any other command.
  protected void gameStartedChecker() throws IllegalStateException {
    if (!gameRunning) {
      throw new IllegalStateException("Game is not started");
    }
  }

  //helper to initialize a board based on sideLength
  private IBoard initBoard(int sideLength) throws IllegalStateException {
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
  private void addStartingMoves() {
    this.board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(Color.BLACK));
    this.board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Color.WHITE));
    this.board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Color.BLACK));
    this.board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Color.WHITE));
    this.board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Color.BLACK));
    this.board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(Color.WHITE));
  }

  @Override
  public boolean validMove(ICell cell) throws IllegalArgumentException {
    return this.board.validMove(cell, getCurrentColor(), false);
  }


  /**
   * Places the current player on the given cell.
   *
   * @param targetCell the cell to place the current player on.
   * @throws IllegalStateException    if the game has not yet started.
   * @throws IllegalArgumentException if the target cell is null.
   */
  @Override
  public void placeCurrentPlayerPiece(ICell targetCell) throws IllegalStateException
          , IllegalArgumentException {
    this.gameStartedChecker();
    if (this.board.validMove(targetCell, this.currentPlayer.getColor(), false)) {
      this.board.validMove(targetCell, this.currentPlayer.getColor(), true);
      this.board.newCellOwner(targetCell, Optional.of(this.currentPlayer.getColor()));
      this.passCount = 0;
    } else {
      throw new IllegalStateException("Invalid move");
    }
    this.passTurn(false);
  }

  @Override
  public void passTurn(boolean increment) throws IllegalStateException {
    if (this.passCount > Color.values().length) {
      throw new IllegalStateException("cannot pass more than " + Color.values().length
              + " times, game should be over");
    }
    this.gameStartedChecker();
    if (increment) {
      this.passCount++;
    }
    if (this.currentPlayer.equals(player1)) {
      this.currentPlayer = this.player2;
    } else {
      this.currentPlayer = this.player1;
    }
  }


  @Override
  public int getDimensions() throws IllegalStateException {
    this.gameStartedChecker();
    return this.sideLength;
  }

  @Override
  public Optional<Color> getCellState(ICell cell) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    return this.board.getCellOccupant(cell);
  }

  @Override
  public int getScore(Color color) throws IllegalStateException {
    return this.board.getColorCount(color);
  }

  @Override
  public Color getCurrentColor() throws IllegalStateException {
    this.gameStartedChecker();
    return this.currentPlayer.getColor();
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    this.gameStartedChecker();
    if (this.passCount >= Color.values().length) {
      this.gameRunning = false;
      return true;
    }

    if (this.board.validMovesLeft(Color.WHITE).isEmpty() &&
            this.board.validMovesLeft(Color.BLACK).isEmpty()) {
      this.gameRunning = false;
      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    return this.board.toString();
  }

  @Override
  public IPlayer getCurrentPlayer() {
    return new HumanPlayer(this.currentPlayer.getColor());
  }

  @Override
  public List<ICell> getValidMoves(Color color) throws IllegalStateException {
    return this.board.validMovesLeft(color);
  }

  @Override
  public int cellsFlipped(ICell cell, Color color) {
    int initialScore = this.getScore(color);
    int finalScore = 0;
    if (this.board.validMove(cell, color, false)) {
      IBoard boardCopy = this.createBoardCopy();
      boardCopy.validMove(cell, color, true);
      finalScore = boardCopy.getColorCount(color);
    }
    return finalScore - initialScore;
  }

  @Override
  public Optional<Color> getWinner() {
    int blackScore = this.getScore(Color.BLACK);
    int whiteScore = this.getScore(Color.WHITE);
    if (blackScore > whiteScore) {
      return Optional.of(Color.BLACK);
    } else if (whiteScore > blackScore) {
      return Optional.of(Color.WHITE);
    }
    return Optional.empty();
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