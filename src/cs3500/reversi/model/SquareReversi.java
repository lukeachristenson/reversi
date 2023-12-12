package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * This class represents a game of SquareReversi. It implements the IReversiModel interface.
 */
public class SquareReversi extends AbstractReversi{
  /**
   * Constructor for a SquareReversi model. Takes in a board, current player, pass count, and
   * whether the game has started or not.
   *
   * @param sideLength the side length of the board.
   * @throws IllegalArgumentException if the side length is not even or less than 2
   */
  public SquareReversi(int sideLength) throws IllegalArgumentException {
    if (sideLength < 2) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    } else if(sideLength % 2 != 0){
      throw new IllegalArgumentException("Side length must be even");
    } else {
      this.sideLength = sideLength;
    }

    this.gameRunning = true;

    this.currentTokenColor = TokenColor.BLACK;
    this.passCount = 0;
    this.board = new SquareBoard(sideLength);
    this.initBoard(sideLength); // rings excluding the center cell = sideLength - 1
    this.addStartingMoves();
    this.modelFeatures = new ArrayList<>();
  }

  /**
   * Constructor for a HexagonReversi model used ONLY for testing. Rigs the game by
   * passing in a hexagonal board for the game.
   *
   * @param squareBoard rigged board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public SquareReversi(IBoard squareBoard, int sideLength) throws IllegalArgumentException {
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    } else {
      this.sideLength = sideLength;
    }

    this.currentTokenColor = TokenColor.BLACK;
    this.passCount = 0;
    this.board = squareBoard;
    this.modelFeatures = new ArrayList<>();
    this.gameRunning = true;
  }

  //helper to initialize a board based on sideLength
  private void initBoard(int sideLength) {
    for (int row = -sideLength/2; row < sideLength/2 + 1; row++) {
      if(row==0) {
        continue;
      }
      for (int col = -sideLength/2; col < sideLength/2 + 1; col++) {
        if(col==0) {
          continue;
        }
        this.board.newCellOwner(new SquareCell(row, col), Optional.empty());
      }
    }
  }

  //helper to add the starting moves of each player
  private void addStartingMoves() {
    board.newCellOwner(new SquareCell(1, 1), Optional.of(TokenColor.BLACK));
    board.newCellOwner(new SquareCell(- 1,  1), Optional.of(TokenColor.WHITE));
    board.newCellOwner(new SquareCell(1, - 1), Optional.of(TokenColor.WHITE));
    board.newCellOwner(new SquareCell(- 1, -1), Optional.of(TokenColor.BLACK));
  }

  @Override
  public IBoard createBoardCopy() {
    Map<ICell, Optional<TokenColor>> mapCopy = this.board.getPositionsMapCopy();
    IBoard copyBoard = new SquareBoard(this.sideLength);

    for (ICell cell : mapCopy.keySet()) {
      copyBoard.newCellOwner(cell, mapCopy.get(cell));
    }
    return copyBoard;
  }
}
