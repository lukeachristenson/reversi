package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;

public class RandomStrat implements Strategy {
  private final TokenColor tokenColor;

  public RandomStrat(TokenColor tokenColor) {
    this.tokenColor = tokenColor;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // return a randomly rearranged list of moves.
    List<ICell> ret = (filteredMoves.isEmpty())?  new ArrayList<>(model.getValidMoves(this.tokenColor)) : new ArrayList<>(filteredMoves);
    Collections.shuffle(ret);
    return ret;
  }
}
