package strategy;

import model.Color;
import model.HexagonReversi;
import model.HumanPlayer;
import model.IBoard;
import model.ICell;
import model.IReversiModel;
import model.ROModel;

import java.util.HashMap;
import java.util.List;


public class MiniMaxStrategy implements Strategy {
  private final Color color;
  private final int depth;

  public MiniMaxStrategy(Color color, int depth) {
    this.color = color;
    this.depth = depth;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // Create a map of cells to their weights.
    HashMap<ICell, Integer> moveScores = new HashMap<>();



    return null;
  }

  private int evaluateMaxScore(ROModel model, int reducedDepth, int originalDepth, Color color) {
    int maxScore = 0;
    int weightFactor;
    HashMap<ICell, Integer> moveScores = new HashMap<>();
    List<ICell> moves  = model.getValidMoves(color);

    // Set the weight factor to -1 if color is opposite to the strategy's color. Else, set it to 1.
    if((originalDepth - reducedDepth)%2 == 0){
      weightFactor = 1;
    } else {
      weightFactor = -1;
    }

    // If the depth is 1, return the number of cells flipped by the move.
    if(reducedDepth == 1) {
      for(ICell cell : moves) {
        if(model.cellsFlipped(cell, color) >= maxScore) {
          maxScore = model.cellsFlipped(cell, color);
        }
      }
      return maxScore * weightFactor;
    } else {
      // For each ICell in moves, map it to the maximum score of the next depth.
      for(ICell cell : moves) {
        IReversiModel modelCopy = new HexagonReversi(model.createBoardCopy(), model.getDimensions());
        modelCopy.placePiece(cell, new HumanPlayer(color));
        moveScores.put(cell,evaluateMaxScore(modelCopy, reducedDepth-1, originalDepth, getOtherColor(color)));
      }
      for(ICell cell : moveScores.keySet()) {


      }
    }


    IBoard board = model.createBoardCopy();


    for(ICell move : moves) {
      int score = evaluateMaxScore(new HexagonReversi(board, model.getDimensions()), moves, reducedDepth - 1, originalDepth, getOtherColor());
      if(score > maxScore) {
        maxScore = score;
      }
    }

    if(reducedDepth == 1) {

    }

    return 0;
  }



  private Color getOtherColor(Color color) {
    if (color.equals(Color.BLACK)) {
      return Color.WHITE;
    }
    return Color.BLACK;
  }


}
