package cs3500.reversi.model;

public class SquareReversi extends HexagonReversi{
  public SquareReversi(int sideLength) throws IllegalArgumentException {
    super(sideLength);
  }

  public SquareReversi(IBoard hexBoard, int sideLength) throws IllegalArgumentException {
    super(hexBoard, sideLength);
  }
}
