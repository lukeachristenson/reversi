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
public class HexagonReversi extends AbstractReversi {
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

  //helper to initialize a board based on sideLength
  protected IBoard initBoard(int sideLength) throws IllegalStateException {
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
    this.board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(TokenColor.WHITE));
    this.board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(TokenColor.BLACK));
    this.board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(TokenColor.WHITE));
    this.board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(TokenColor.BLACK));
    this.board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(TokenColor.WHITE));
    this.board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(TokenColor.BLACK ));
  }

}
