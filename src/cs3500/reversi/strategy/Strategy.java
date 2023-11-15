package cs3500.reversi.strategy;

import java.util.List;

import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;

/**
 * Represents a strategy for choosing a move in a game of Reversi. The strategy may be based on a
 * single algorithm or a combination of algorithms(e.g. in Sandwich).
 */
public interface Strategy {
  List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves);
}