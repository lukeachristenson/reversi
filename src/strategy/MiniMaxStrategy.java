package strategy;

import model.Color;
import model.HexagonCell;
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
      // Create a copy of the board and make the move on the copy, then create a copy of the model
      // to pass to the scoreDifference method.
      IBoard boardCopy = model.createBoardCopy();
      boardCopy.validMove(cell, color, true);
      IReversiModel modelCopy = new HexagonReversi(boardCopy, model.getDimensions());

      // If the game is over, return the score difference.
      if(modelCopy.isGameOver()) {
        if(model.getWinner().isEmpty()) {
          moveScores.put(cell, 0);
          continue;
        }
        if(model.getWinner().get().equals(this.getOtherColor(color))) {
         moveScores.put(cell, -10000); // Case where opponent color wins.
          continue;
        } else {
          moveScores.put(cell, 10000); // Case where strategy color wins.
          continue;
        }
      }
      // Evaluate the score difference of the move and put it in the hashMap.
      moveScores.put(cell, scoreDifference(modelCopy, this.getOtherColor(color)));
    }

    int maxScoreDifference = Integer.MIN_VALUE;
    ICell bestMove = null;
    // Find the max score difference in the possible moves.
    for(ICell cell : moveScores.keySet()) {
      if(moveScores.get(cell) > maxScoreDifference) {
        maxScoreDifference = moveScores.get(cell);
        bestMove = cell;
      }
    }

    System.out.println(moveScores.get(new HexagonCell(1, 1, -2)));
    System.out.println(moveScores.get(new HexagonCell(-1, -1, 2)));

    // For the case where passing is simply the best move.
    IReversiModel modelCopy = new HexagonReversi(model.createBoardCopy(), model.getDimensions());
    modelCopy.passTurn(false);
    int passingScoreDifference = scoreDifference(modelCopy, this.getOtherColor(color));
    if(passingScoreDifference > maxScoreDifference) {
      maxScoreDifference =  passingScoreDifference;
      return List.of(null);
    }
    return List.of(bestMove);
  }

  private int scoreDifference(IReversiModel model, Color opponentColor) {
    int maxScoreDifference = Integer.MIN_VALUE;

    // Make the model's current player the color passed in.
    if(!model.getCurrentColor().equals(opponentColor)) {
      model.passTurn(false);
    }

    for(ICell cell : model.getValidMoves(opponentColor)) {
      IReversiModel modelCopy = new HexagonReversi(model.createBoardCopy(), model.getDimensions());
      modelCopy.placeCurrentPlayerPiece(cell);

      // If the game is over, return the score difference.
      if(modelCopy.isGameOver()) {
        if(model.getWinner().isEmpty()) {
          maxScoreDifference =  0;
        }
        if(model.getWinner().get().equals(opponentColor)) {
          maxScoreDifference =  -10000; // Case where opponent color wins.
        } else {
          maxScoreDifference =  10000; // Case where strategy color wins.
        }
      }

      if(modelCopy.getScore(this.color) - modelCopy.getScore(opponentColor) > maxScoreDifference) {
        maxScoreDifference =  modelCopy.getScore(this.color) - modelCopy.getScore(opponentColor);
      }
    }
    // Case where passing is the best move for the player for some reason.
    IReversiModel modelCopy = new HexagonReversi(model.createBoardCopy(), model.getDimensions());
    modelCopy.passTurn(false);
    if(modelCopy.getScore(this.color) - modelCopy.getScore(opponentColor) > maxScoreDifference) {
      maxScoreDifference =  modelCopy.getScore(this.color) - modelCopy.getScore(opponentColor);
    }

    return maxScoreDifference;
  }

  private Color getOtherColor(Color color) {
    if (color.equals(Color.BLACK)) {
      return Color.WHITE;
    }
    return Color.BLACK;
  }
}
