package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;

/**
 * A class that represents a strategy that avoids edges(cells adjacent to corners).
 */
public class AvoidEdgesStrat implements Strategy {
  private final TokenColor tokenColor;

  /**
   * Constructs a new AvoidEdges strategy.
   *
   * @param tokenColor the color of the player using this strategy.
   */
  public AvoidEdgesStrat(TokenColor tokenColor) {
    this.tokenColor = tokenColor;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    List<ICell> choices = (filteredMoves.isEmpty())
            ? model.createBoardCopy().validMovesLeft(tokenColor) : filteredMoves;
    List<ICell> retList = new ArrayList<>(choices);

    System.out.println("AvoidEdgesStrat");
    System.out.println(model.getEdgeCells().size());
    // Removes all cells adjacent to corners from the list of available moves.
    for(ICell cell : model.getEdgeCells()) {
      if (choices.contains(cell)) {
        retList.remove(cell);
      }
    }

    //if the only available moves were edgeMoves, brings back the option to play them.
    if (retList.isEmpty()) {
      return choices;
    }
    return retList;
  }
}
