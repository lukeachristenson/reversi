import java.util.List;

import model.Color;
import model.IBoard;
import model.ICell;
import model.IPlayer;
import model.ROModel;

public class MockModel implements ROModel {
  private final IBoard board;
  public MockModel(int sideLength, IBoard board) throws IllegalArgumentException {

    this.board = board;
  }

  @Override
  public IPlayer getCurrentPlayer() throws IllegalStateException {
    return null;
  }

  @Override
  public IBoard createBoardCopy() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Color getCurrentColor() {
    return null;
  }

  @Override
  public int getDimensions() {
    return 0;
  }

  @Override
  public List<ICell> getValidMoves(Color color) {
    return null;
  }
}