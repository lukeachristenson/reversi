package strategy;

import java.util.List;
import java.util.Optional;

import model.ICell;
import model.IReversiModel;
import model.ROModel;

/**
 * Represents a strategy for choosing a move in a game of Reversi. The strategy may be based on a
 * single algorithm or a combination of algorithms(e.g. in Sandwich).
 */
public interface Strategy {
  List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves);
}