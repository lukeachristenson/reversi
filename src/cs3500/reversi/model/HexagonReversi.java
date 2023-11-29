package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.controller.IModelFeature;

/**
 * This class represents a game of HexagonReversi. It implements the IReversiModel interface.
 * HexReversi is a hexagonal version of the game Reversi.
 */
public class HexagonReversi implements IReversiModel {
  private final IBoard board;
  private final int sideLength;
  private TokenColor currentTokenColor;
  private int passCount;
  private boolean gameRunning;
  private final List<IModelFeature> modelFeatures;


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

    this.currentTokenColor = TokenColor.BLACK;
    this.passCount = 0;
    this.board = this.initBoard(sideLength); // rings excluding the center cell = sideLength - 1
    this.addStartingMoves();
    this.modelFeatures = new ArrayList<>();
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

    this.currentTokenColor = TokenColor.BLACK;
    this.passCount = 0;
    this.board = hexBoard;
    this.modelFeatures = new ArrayList<>();
    this.gameRunning = true;
  }

  @Override
  public void startGame() {
    this.gameRunning = true;
    this.emitMessage();
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
    int rings = sideLength - 1;
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
  public boolean validMove(ICell cell) throws IllegalArgumentException {
    return this.board.validMove(cell, getCurrentColor(), false);
  }

  @Override
  public void addListener(IModelFeature listener) {
    this.modelFeatures.add(listener);
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
    if (this.board.validMove(targetCell, this.currentTokenColor, false)) {
      this.board.validMove(targetCell, this.currentTokenColor, true);
      this.board.newCellOwner(targetCell, Optional.of(this.currentTokenColor));
      this.passCount = 0;
    } else {
      throw new IllegalStateException("Invalid move");
    }
    this.passTurn(false);
    emitMessage();
  }

  private void emitMessage() {
    for (IModelFeature listener : this.modelFeatures) {
      listener.notifyPlayerMove(this.getCurrentColor());
    }
  }

  @Override
  public void passTurn(boolean increment) throws IllegalStateException {
    if (this.passCount > TokenColor.values().length) {
      throw new IllegalStateException("cannot pass more than " + TokenColor.values().length
              + " times, game should be over");
    }
    this.gameStartedChecker();
    if (increment) {
      this.passCount++;
    }

    if (this.currentTokenColor.equals(TokenColor.BLACK)) {
      this.currentTokenColor = TokenColor.WHITE;
    } else {
      this.currentTokenColor = TokenColor.BLACK;
    }

    emitMessage();
  }


  @Override
  public int getDimensions() throws IllegalStateException {
    this.gameStartedChecker();
    return this.sideLength;
  }

  @Override
  public Optional<TokenColor> getCellState(ICell cell) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    return this.board.getCellOccupant(cell);
  }

  @Override
  public int getScore(TokenColor tokenColor) throws IllegalStateException {
    return this.board.getColorCount(tokenColor);
  }

  @Override
  public TokenColor getCurrentColor() throws IllegalStateException {
//    this.gameStartedChecker();
    return this.currentTokenColor;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
//    this.gameStartedChecker();
    if (this.passCount >= TokenColor.values().length) {
      this.gameRunning = false;
      return true;
    }

    if (this.board.validMovesLeft(TokenColor.WHITE).isEmpty() &&
            this.board.validMovesLeft(TokenColor.BLACK).isEmpty()) {
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
  public List<ICell> getValidMoves(TokenColor tokenColor) throws IllegalStateException {
    return this.board.validMovesLeft(tokenColor);
  }

  @Override
  public int cellsFlipped(ICell cell, TokenColor tokenColor) {
    int initialScore = this.getScore(tokenColor);
    int finalScore = 0;
    if (this.board.validMove(cell, tokenColor, false)) {
      IBoard boardCopy = this.createBoardCopy();
      boardCopy.validMove(cell, tokenColor, true);
      finalScore = boardCopy.getColorCount(tokenColor);
    }
    return finalScore - initialScore;
  }

  @Override
  public Optional<TokenColor> getWinner() {
    int blackScore = this.getScore(TokenColor.BLACK);
    int whiteScore = this.getScore(TokenColor.WHITE);
    if (blackScore > whiteScore) {
      return Optional.of(TokenColor.BLACK);
    } else if (whiteScore > blackScore) {
      return Optional.of(TokenColor.WHITE);
    }
    return Optional.empty();
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
