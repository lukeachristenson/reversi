package mockmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.controller.IModelFeature;
import cs3500.reversi.model.HexagonBoard;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;

/**
 * This class represents a mock model for testing purposes. Keeps a log of when the methods are
 * called by the controller(s).
 */
public class MockModel implements IReversiModel {
  private final StringBuilder log;
  private final List<IModelFeature> modelFeatures;

  /**
   * Constructs a mock model with the given log.
   *
   * @param log the log to keep track of when methods are called.
   */
  public MockModel(StringBuilder log) {
    this.log = log;
    this.modelFeatures = new ArrayList<>();
  }

  public void startGame() {
    this.log.append("startGame called\n");
    this.emitMessage();
  }

  protected void gameStartedChecker() throws IllegalStateException {
    this.log.append("gameStartedChecker called\n");
  }

  //helper to initialize a board based on sideLength
  private IBoard initBoard(int sideLength) throws IllegalStateException {
    // This does not concern testing.
    return null;
  }

  //helper to add the starting moves of each player
  private void addStartingMoves() {
    // This does not concern testing.
  }

  @Override
  public boolean validMove(ICell cell) throws IllegalArgumentException {
    // This does not concern testing.
    log.append("validMove called with ICell:" + cell.getCoordinates() + "\n");
    return true;
  }

  @Override
  public void addListener(IModelFeature listener) {
    this.log.append("addListener called to add a ModelFeature\n");
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
  public void placeCurrentPlayerPiece(ICell targetCell) {
    this.log.append("placeCurrentPlayerPiece called with ICell:" + targetCell.getCoordinates() + "\n");
    emitMessage();
  }

  private void emitMessage() {
    this.log.append("emitMessage called\n");
    for (ModelFeatures listener : this.modelFeatures) {
      this.log.append("emitColor called on single listener\n");
      listener.emitMoveColor(this.getCurrentColor());
    }
  }

  @Override
  public void passTurn(boolean increment) throws IllegalStateException {


    emitMessage();
  }


  @Override
  public int getDimensions() throws IllegalStateException {
    // This does not concern testing.
    return 6;
  }

  @Override
  public Optional<TokenColor> getCellState(ICell cell) throws IllegalArgumentException
          , IllegalStateException {
    // This does not concern testing.
    return Optional.empty();
  }

  @Override
  public int getScore(TokenColor tokenColor) throws IllegalStateException {
    // This does not concern testing.
    return 0;
  }

  @Override
  public TokenColor getCurrentColor() throws IllegalStateException {
    this.log.append("getCurrentColor called\n");
    return TokenColor.BLACK;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    this.log.append("isGameOver called\n");
    return false;
  }

  @Override
  public String toString() {
    // This does not concern testing.
    return "Model to String";
  }


  @Override
  public List<ICell> getValidMoves(TokenColor tokenColor) throws IllegalStateException {
    this.log.append("getValidMoves called\n");
    return new ArrayList<>();
  }

  @Override
  public int cellsFlipped(ICell cell, TokenColor tokenColor) {
    this.log.append("cellsFlipped called\n");
    return 0;
  }

  @Override
  public Optional<TokenColor> getWinner() {
    this.log.append("getWinner called\n");
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
    // This does not concern testing.
    this.log.append("createBoardCopy called\n");
    return new HexagonBoard(6);
  }
}
