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
public class AbstractReversi implements IReversiModel{
  protected  IBoard board;
  protected int sideLength;
  protected TokenColor currentTokenColor;
  protected int passCount;
  protected boolean gameRunning;
  protected  List<IModelFeature> modelFeatures;

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

  @Override
  public boolean validMove(ICell cell) throws IllegalArgumentException {
    return this.board.validMove(cell, getCurrentColor(), false);
  }

  @Override
  public List<ICell> getCornerCells() {
    List<ICell> cornerCells = new ArrayList<>();
    for(ICell cell : this.board.getPositionsMapCopy().keySet()) {
      if (cell.getCoordinates().get(0) == this.sideLength - 1
              || cell.getCoordinates().get(1) == this.sideLength - 1
              || cell.getCoordinates().get(2) == this.sideLength - 1) {
        cornerCells.add(cell);
      }
    }
    return cornerCells;
  }

  @Override
  public List<ICell> getEdgeCells() {
    List<ICell> edgeCells = new ArrayList<>();
    List<ICell> cornerCells = this.getCornerCells();

    // difference in q direction
    int[] dq = this.board.getBoardDirections()[0];
    // difference in r direction
    int[] dr = this.board.getBoardDirections()[1];
    // difference in s direction
    int[] ds = this.board.getBoardDirections()[2];

    for (ICell corner : cornerCells) {
      //six directions from every cell
      for (int i = 0; i < 6; i++) {
        int q = corner.getCoordinates().get(0) + dq[i];
        int r = corner.getCoordinates().get(1) + dr[i];
        int s = corner.getCoordinates().get(2) + ds[i];
        ICell cell = new HexagonCell(q, r, s);
        edgeCells.add(cell);
      }
    }

    return edgeCells;
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

  protected void emitMessage() {
    try {
      for (IModelFeature listener : this.modelFeatures) {
        listener.notifyPlayerMove(this.getCurrentColor());
      }
    } catch (Exception ignored) {

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
    return this.currentTokenColor;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
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
