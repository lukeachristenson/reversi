package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;

/**
 * Represents a strategy that chooses a random move from the list of valid moves.
 * Note: This can be considered as a "dumb" strategy and is made with the understanding that it
 * may simulate a random Human Player's moves.
 */
public class RandomStrat implements Strategy {
  private final TokenColor tokenColor;

  /**
   * Constructs a RandomStrat with the given token color.
   * @param tokenColor  the token color of the player using this strategy
   */
  public RandomStrat(TokenColor tokenColor) {
    this.tokenColor = tokenColor;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // return a randomly rearranged list of moves.
    List<ICell> ret = (filteredMoves.isEmpty()) ?
            new ArrayList<>(model.getValidMoves(this.tokenColor)) : new ArrayList<>(filteredMoves);
    Collections.shuffle(ret);
    return ret;
  }
}
