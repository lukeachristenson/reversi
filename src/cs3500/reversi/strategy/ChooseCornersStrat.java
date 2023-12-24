package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;

/**
 * This class represents a strategy that chooses the corners of the board if possible.
 */
public class ChooseCornersStrat implements Strategy {
  private final TokenColor tokenColor;

  /**
   * Constructor for a ChooseCorners strategy.
   *
   * @param tokenColor the color of the player using this strategy.
   */
  public ChooseCornersStrat(TokenColor tokenColor) {
    this.tokenColor = tokenColor;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    IBoard board = model.createBoardCopy();
    List<ICell> choices = (filteredMoves.isEmpty()) ? board.validMovesLeft(tokenColor) :
            filteredMoves;
    List<ICell> retList = new ArrayList<>();
    int sideLength = model.getDimensions();


    // Adds all corner cells to the list of available moves.
    for(ICell cell : model.getCornerCells()) {
      if(choices.contains(cell)) {
        retList.add(cell);
      }
    }

    if (retList.isEmpty()) {
      return choices;
    }
    return retList;
  }
}
