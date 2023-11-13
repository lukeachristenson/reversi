package strategy;

import java.util.List;

import model.Color;
import model.ICell;
import model.ROModel;

public class Greedy implements Strategy{
  private final model.Color color;

  public Greedy(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    List<ICell> choices = (filteredMoves.isEmpty()) ? model.createBoardCopy().validMovesLeft(color) : filteredMoves;
    ICell retCell = null;
    int minScoreDiff = 0;
    int existingScore = model.createBoardCopy().getColorCount(color);

    for(ICell cell : choices) {
      model.IBoard board = model.createBoardCopy();
      board.validMove(cell, color, true);
      int scoreDiff = board.getColorCount(color) - existingScore;
      if(scoreDiff > minScoreDiff) {
        minScoreDiff = scoreDiff;
        retCell = cell;
      }
    }

    return List.of(retCell);
  }
}
