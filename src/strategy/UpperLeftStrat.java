package strategy;

import java.util.List;
import java.util.Optional;

import model.Color;
import model.IBoard;
import model.ICell;
import model.ROModel;

public class UpperLeftStrat implements Strategy {
  private final Color color;

  public UpperLeftStrat(Color color, ROModel model) {
    this.color = color;
  }


  // TODO: Need to make this select the uppermost-leftmost move.
  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    ICell returnCell = null;
    List<ICell> choices = (filteredMoves.isEmpty()) ? model.createBoardCopy().validMovesLeft(color) : filteredMoves;
    if (model.getCurrentColor().equals(color)) {
      // Get leftmost and uppermost move.
      // Get cell with maximum s, minimum r --> (s - r) = maximum.
      int total = Integer.MIN_VALUE;
      for (ICell cell : choices) {
        int compareVal = cell.getCoordinates().get(2) - cell.getCoordinates().get(1);
        if (compareVal > total) {
          total = compareVal;
          returnCell = cell;
        }
      }
      return List.of(returnCell);
    }
    return List.of();
  }
}