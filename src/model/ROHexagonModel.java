package model;

import java.util.Optional;

public class ROHexagonModel extends HexagonReversi{
  public ROHexagonModel(int sideLength) throws IllegalArgumentException {
    super(sideLength);
  }

  public ROHexagonModel(IBoard hexBoard, int sideLength) throws IllegalArgumentException {
    super(hexBoard, sideLength);
  }

  @Override
  public void placePiece(ICell targetCell, IPlayer player) throws IllegalStateException
          , IllegalArgumentException {
    this.gameStartedChecker();
  }

  @Override
  public void passTurn() {
  }

}
