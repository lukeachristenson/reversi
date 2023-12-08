package cs3500.reversi.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;


/**
 * This class represents a strategy for the game of HexagonReversi. This strategy will choose the
 * move  based on the move that will result in the highest score difference between
 * the strategy's color and the opponent's color.
 */
public class MiniMaxStrategy implements Strategy {
  private final TokenColor tokenColor;

  /**
   * Constructor for a MiniMaxStrategy. Takes in the color of the strategy.
   *
   * @param tokenColor the color of the strategy.
   */
  public MiniMaxStrategy(TokenColor tokenColor) {
    this.tokenColor = tokenColor;
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
    return filteredMoves.isEmpty() ? model.createBoardCopy().validMovesLeft(tokenColor) :
            filteredMoves;
  }

  // Evaluate each possible move and calculate the score difference.
  private Map<ICell, Integer> evaluateMovesAndGetScores(ROModel model, List<ICell> choices) {
    Map<ICell, Integer> moveScores = new HashMap<>();
    for (ICell cell : choices) {
       // Put the score difference for each move into a map using the model copy.


       // **** NEW IMPLEMENTAION: USES ROMODEL ****
       moveScores.put(cell, calculateScoreDifference(model, Optional.of(cell)));

    }
    return moveScores;
  }

  // Calculate the score difference between the player's color and the opponent.
  private int calculateScoreDifference(ROModel model, Optional<ICell> cell) {
    // If the game is over, skew the score difference to indicate a win or loss.
    if (model.isGameOver()) {
      return model.getWinner().
              map(winner -> winner == tokenColor ? 10000 : -10000).orElse(0);
    }

    // If the game isn't over, return the score difference after the move(or pass if the parameter
    // is empty) is made.
    IBoard boardCopy = model.createBoardCopy();
    cell.ifPresent(c -> boardCopy.validMove(c, tokenColor, true));

    return boardCopy.getColorCount(tokenColor) - boardCopy.getColorCount(getOtherColor(tokenColor));
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
    // Calculate the score difference for passing the turn.

    // **** NEW IMPLEMENTAION: USES ROMODEL ****
    int passingScoreDifference = calculateScoreDifference(model, Optional.empty());
    return passingScoreDifference > maxScoreDifference;
  }

  // Utility method to get the color opposite to the one provided.
  private TokenColor getOtherColor(TokenColor tokenColor) {
    return tokenColor == TokenColor.BLACK ? TokenColor.WHITE : TokenColor.BLACK;
  }
}
