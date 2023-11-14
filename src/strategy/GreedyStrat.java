package strategy;

import java.util.ArrayList;
import java.util.List;

import model.Color;
import model.ICell;
import model.ROModel;

public class GreedyStrat implements Strategy{
  private final model.Color color;

  public GreedyStrat(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    List<ICell> choices = (filteredMoves.isEmpty()) ? model.createBoardCopy().validMovesLeft(color) : filteredMoves;
    ICell retCell = null;
    List<ICell> retList = new ArrayList<>();
    int minScoreDiff = 0;

    // Evaluate the maximum score difference in score for each of the cells.
    for(ICell cell : choices) {
      int scoreDiff = model.cellsFlipped(cell, color);
      if(scoreDiff > minScoreDiff) {
        minScoreDiff = scoreDiff;
        retCell = cell;
      }
    }

    // Add the cells with the maximum cells flipped to the return list.
    for(ICell cell : choices) {
      int scoreDiff = model.cellsFlipped(cell, color);
      if(scoreDiff == minScoreDiff) {
        retList.add(cell);
      }
    }

    return new UpperLeftStrat(color).chooseMove(model, retList);
  }
}
