package strategy;

import java.util.List;
import java.util.Optional;

import model.Color;
import model.IBoard;
import model.ICell;
import model.ROModel;

public class Sandwich implements Strategy {
  private final Color color;
  private final IBoard board;
  private final Strategy strat1;
  private final Strategy strat2;

  public Sandwich(Color color, IBoard board, Strategy strat1, Strategy strat2) {
    this.color = color;
    this.board = board;
    this.strat1 = strat1;
    this.strat2 = strat2;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // Use Strat1 to get the best move. Pass the list of moves to the next strategy.
    List<ICell> filter = (strat1.chooseMove(model, filteredMoves).isEmpty()) ? filteredMoves
            : strat1.chooseMove(model, filteredMoves);
    return strat2.chooseMove(model, filter);

  }
}
