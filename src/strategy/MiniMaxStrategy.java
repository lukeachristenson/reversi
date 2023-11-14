package strategy;

import model.Color;
import model.HexagonReversi;
import model.IBoard;
import model.ICell;
import model.IReversiModel;
import model.ROModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MiniMaxStrategy implements Strategy {
  private final Color color;

  public MiniMaxStrategy(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    List<ICell> choices = (filteredMoves.isEmpty()) ? model.createBoardCopy().validMovesLeft(color)
            : filteredMoves;

    // Initiate a hashMap to evaluate map each cell's score difference.
    HashMap<ICell, Integer> moveScores = new HashMap<>();

    for(ICell cell : choices) {
      IBoard boardCopy =
      IReversiModel model = new
    }

    return null;
  }


  private int scoreDifference(IReversiModel model, Color color) {


    return 0;
  }


  private Color getOtherColor(Color color) {
    if (color.equals(Color.BLACK)) {
      return Color.WHITE;
    }
    return Color.BLACK;
  }


}
