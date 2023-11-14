package model;

public class ROHexagonModel extends HexagonReversi{
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
