package cs3500.reversi.strategy;

import java.util.List;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;

/**
 * This class represents a greedy strategy for the game of HexReversi.
 */
public class HumanStrat implements Strategy {
  private final TokenColor tokenColor;

  /**
   * Constructor for a GreedyStrat. Takes in a color for the player.
   *
   * @param tokenColor the color of the player.
   */
  public HumanStrat(TokenColor tokenColor) {
    this.tokenColor = tokenColor;
  }


  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    return null; // Return null because the human player does not make moves through the strategy.
  }
}
