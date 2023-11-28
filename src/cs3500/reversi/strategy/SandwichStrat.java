package cs3500.reversi.strategy;

import java.util.List;

import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;

/**
 * Represents a composite strategy that uses a list of strategies to determine the best move.
 */
public class SandwichStrat implements Strategy {
  private final TokenColor tokenColor;
  private final List<Strategy> strategyList;

  /**
   * Constructs a Sandwich strategy with the given color and list of strategies.
   *
   * @param tokenColor   the color of the player using this strategy
   * @param strategyList the list of strategies to use in order
   */
  public SandwichStrat(TokenColor tokenColor, List<Strategy> strategyList) {
    this.tokenColor = tokenColor;
    this.strategyList = strategyList;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // Use Strat1 to get the best move. Pass the list of moves to the next strategy.
    List<ICell> filter = (filteredMoves.isEmpty()) ? model.getValidMoves(tokenColor)
            : filteredMoves;

    for (int i = 0; i < strategyList.size(); i++) {
      List<ICell> newFilter = strategyList.get(i).chooseMove(model, filter);
      filter = newFilter;
    }
    return filter;
  }
}
