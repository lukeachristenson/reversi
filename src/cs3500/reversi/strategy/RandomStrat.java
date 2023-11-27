package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;

public class RandomStrat implements Strategy {
  private final Color color;

  public RandomStrat(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // return a randomly rearranged list of moves.
    List<ICell> ret = (filteredMoves.isEmpty())?  new ArrayList<>(model.getValidMoves(this.color)) : new ArrayList<>(filteredMoves);
    Collections.shuffle(ret);
    return ret;
  }
}
