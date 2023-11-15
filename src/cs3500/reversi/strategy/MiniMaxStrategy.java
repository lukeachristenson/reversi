package cs3500.reversi.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.ROModel;


/**
 * This class represents a strategy for the game of HexagonReversi. This strategy will choose the
 * move  based on the move that will result in the highest score difference between
 * the strategy's color and the opponent's color.
 */
public class MiniMaxStrategy implements Strategy {
  private final Color color;

  /**
   * Constructor for a MiniMaxStrategy. Takes in the color of the strategy.
   *
   * @param color the color of the strategy.
   */
  public MiniMaxStrategy(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // Get valid choices (moves) based on the current state of the game.
    List<ICell> choices = getValidChoices(model, filteredMoves);

    // Evaluate all possible moves and calculate score differences for each.
    Map<ICell, Integer> moveScores = evaluateMovesAndGetScores(model, choices);

    // Determine the best move based on score differences.
    return getBestMove(moveScores, model);
  }

  // Get a list of valid moves, either from filteredMoves or by calculating.
  private List<ICell> getValidChoices(ROModel model, List<ICell> filteredMoves) {
    return filteredMoves.isEmpty() ? model.createBoardCopy().validMovesLeft(color) : filteredMoves;
  }

  // Evaluate each possible move and calculate the score difference.
  private Map<ICell, Integer> evaluateMovesAndGetScores(ROModel model, List<ICell> choices) {
    Map<ICell, Integer> moveScores = new HashMap<>();
    for (ICell cell : choices) {
      IReversiModel modelCopy = new HexagonReversi(model.createBoardCopy(), model.getDimensions());
      modelCopy.validMove(cell);
      moveScores.put(cell, calculateScoreDifference(modelCopy));
    }
    return moveScores;
  }

  // Calculate the score difference between the player's color and the opponent.
  private int calculateScoreDifference(IReversiModel modelCopy) {
    if (modelCopy.isGameOver()) {
      return modelCopy.getWinner().map(winner -> winner == color ? 10000 : -10000).orElse(0);
    }
    return modelCopy.getScore(color) - modelCopy.getScore(getOtherColor(color));
  }

  // Determine the best move based on score differences and check if passing is a better option.
  private List<ICell> getBestMove(Map<ICell, Integer> moveScores, ROModel model) {
    int maxScoreDifference = Integer.MIN_VALUE;
    ICell bestMove = null;

    for (Map.Entry<ICell, Integer> entry : moveScores.entrySet()) {
      if (entry.getValue() > maxScoreDifference) {
        maxScoreDifference = entry.getValue();
        bestMove = entry.getKey();
      }
    }

    if (shouldPass(model, maxScoreDifference)) {
      return List.of();
    }

    return List.of(bestMove);
  }

  // Determine if passing the turn results in a better outcome than the current best move.
  private boolean shouldPass(ROModel model, int maxScoreDifference) {
    IReversiModel modelCopy = new HexagonReversi(model.createBoardCopy(), model.getDimensions());
    modelCopy.passTurn(false);
    int passingScoreDifference = calculateScoreDifference(modelCopy);
    return passingScoreDifference > maxScoreDifference;
  }

  // Utility method to get the color opposite to the one provided.
  private Color getOtherColor(Color color) {
    return color == Color.BLACK ? Color.WHITE : Color.BLACK;
  }
}
