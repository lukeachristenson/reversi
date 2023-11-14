package strategy;

import model.Color;
import model.HexagonReversi;
import model.IBoard;
import model.ICell;
import model.IReversiModel;
import model.ROModel;

import java.util.HashMap;
import java.util.List;


public class MiniMaxStrategy implements Strategy {
  private final Color color;

  public MiniMaxStrategy(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // Create a map of cells to their weights.
    HashMap<ICell, Integer> moveScores = new HashMap<>();



    for (ICell cell : model.getValidMoves(color)) {
      // Create model copy with the board.
      IReversiModel newModel = new HexagonReversi(model.createBoardCopy(), model.getDimensions());

      // If for any cell in the list of valid moves left, the game is over, add the weight of the cell accordingly.

      // Else, evaluate the opponent's best move and add the weight of the cell accordingly.


      int weight = model.cellsFlipped(cell, color);
      IBoard newBoard = model.createBoardCopy();
      newBoard.validMove(cell, color, true);
      IReversiModel newModel = new HexagonReversi(newBoard, model.getDimensions());
      moveScores.put(cell, weight + evaluateOpponentMax(newModel));
    }

    return null;
  }

  private int evaluateOpponentMax(ROModel model) {
    List<ICell> validMoves = model.getValidMoves(getOtherColor(color));
    int max = Integer.MIN_VALUE;

    if (model.isGameOver()) {
      // If
      if (model.getScore(color) < model.getScore(getOtherColor(color))) {
        return Integer.MIN_VALUE;
      } else {
        return Integer.MAX_VALUE;
      }
    }

    for (ICell cell : validMoves) {
      int score = model.cellsFlipped(cell, color);
      if (score > max) {
        max = score;
      }
    }

    return -max;
  }


  private int returnGameOverWeight(ROModel model) {
    if (model.isGameOver()) {
      // If
      if (model.getScore(color) < model.getScore(this.getOtherColor(color))) {
        return Integer.MIN_VALUE;
      } else {
        return Integer.MAX_VALUE;
      }
    }
    return 0;
  }


  private Color getOtherColor(Color color) {
    if (color.equals(Color.BLACK)) {
      return Color.WHITE;
    } else
      return Color.BLACK;
  }
}

