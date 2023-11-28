package cs3500.reversi.strategy;

import java.util.List;

import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;

/**
 * Represents a strategy for choosing a move in a game of Reversi. The strategy may be based on a
 * single algorithm or a combination of algorithms(e.g. in Sandwich).
 */
public interface Strategy {

  /**
   * Chooses a move from the given list of moves. If the list is empty, returns a list of moves
   * where the first ICell is the best move for the given strategy.
   * @param model the model to choose a move from
   * @param filteredMoves the list of moves to choose from
   * @return  a list of moves where the first ICell is the best move for the given strategy
   */
  List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves);
}