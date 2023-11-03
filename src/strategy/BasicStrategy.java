package strategy;

import model.ICell;
import model.IReversiModel;
import model.Color;

public class BasicStrategy implements Strategy{
  private final Color color;

  public BasicStrategy(Color color) {
    this.color = color;
  }

  @Override
  public ICell chooseMove(IReversiModel model) {
    return model.getValidMoves(this.color).get(0);
  }


}
