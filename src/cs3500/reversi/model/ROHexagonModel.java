package cs3500.reversi.model;

/**
 * This class represents a Hexagon Reversi model that is read-only. It is used for the view and
 * strategies and to get observations from the move.
 */
public class ROHexagonModel extends HexagonReversi {
  public ROHexagonModel(int sideLength) throws IllegalArgumentException {
    super(sideLength);
  }

  public ROHexagonModel(IBoard hexBoard, int sideLength) throws IllegalArgumentException {
    super(hexBoard, sideLength);
  }

  @Override
  public void passTurn(boolean increment) throws IllegalStateException {
    this.gameStartedChecker();
  }
}
