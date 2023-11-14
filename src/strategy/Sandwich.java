package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Color;
import model.IBoard;
import model.ICell;
import model.ROModel;

public class Sandwich implements Strategy {
  private final Color color;
  private final List<Strategy> strategyList;

  public Sandwich(Color color, List<Strategy> strategyList) {
    this.color = color;
    this.strategyList = strategyList;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // Use Strat1 to get the best move. Pass the list of moves to the next strategy.
    List<ICell> filter = (filteredMoves.isEmpty()) ? model.getValidMoves(color)
            : filteredMoves;

    for(int i = 0 ; i < strategyList.size() ; i++) {
      List<ICell> newFilter = strategyList.get(i).chooseMove(model, filter);
      filter = newFilter;
    }
    return filter;
  }
}
