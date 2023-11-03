package strategy;

import model.ICell;
import model.IReversiModel;

public interface Strategy {
  ICell chooseMove(IReversiModel model);
}
