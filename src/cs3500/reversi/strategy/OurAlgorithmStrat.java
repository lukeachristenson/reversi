package cs3500.reversi.strategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;

/**
 * This class represents a strategy for the game of HexagonReversi. This strategy will choose the
 * best move based on the move that will result in the highest score difference between the
 * strategy's color and the opponent's color. This decision is made my playing every possible move,
 * then playing every logical response to that move, and so on until the game is over. In this way,
 * it prunes the number of situations to test by finding the logical moves of the opponent in the
 * first place.
 */
public class OurAlgorithmStrat implements Strategy {
  private final TokenColor tokenColor;

  /**
   * Constructor for a MiniMaxStrategyV2. Takes in a color for the player.
   *
   * @param tokenColor the color of the player.
   */
  public OurAlgorithmStrat(TokenColor tokenColor) {
    this.tokenColor = tokenColor;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    List<ICell> choices = getChoices(model, filteredMoves);
    HashMap<ICell, Integer> moveScores = evaluateMoves(model, choices);

    return getOptimalMove(moveScores, choices);
  }

  // gets the move choices of strategy colors player
  private List<ICell> getChoices(ROModel model, List<ICell> filteredMoves) {
    return filteredMoves.isEmpty() ? model.createBoardCopy().validMovesLeft(tokenColor) : filteredMoves;
  }

  // evaluates the moves of the strategy color player, and opponents highest scoring response
  private HashMap<ICell, Integer> evaluateMoves(ROModel model, List<ICell> choices) {
    HashMap<ICell, Integer> moveScores = new HashMap<>();
    for (ICell cell : choices) {
      IReversiModel modelCopy = createModelCopyWithMove(model, cell);
      if (playerWonGame(modelCopy)) {
        return new HashMap<>(Collections.singletonMap(cell, 0));
      }
      moveScores.put(cell, getOpponentHighestScore(modelCopy));
    }
    return moveScores;
  }

  // creates a model copy with the move of the strategy color player
  private IReversiModel createModelCopyWithMove(ROModel model, ICell cell) {
    IBoard board = model.createBoardCopy();
    IReversiModel modelCopy = new HexagonReversi(board, model.getDimensions());
    modelCopy.placeCurrentPlayerPiece(cell);
    return modelCopy;
  }

  // gets the highest score of the opponent color player for a given
  // move of the strategy color player
  private int getOpponentHighestScore(IReversiModel modelCopy) {
    List<ICell> opponentResponses = opponentMoves(modelCopy);
    return opponentResponses.stream()
            .mapToInt(response -> evaluateOpponentMove(modelCopy, response))
            .max()
            .orElse(0);
  }

  // evaluates the score of the opponent color player for a given move of the strategy color player
  private int evaluateOpponentMove(IReversiModel modelCopy, ICell opponentMove) {
    IReversiModel newModelCopy =
            new HexagonReversi(modelCopy.createBoardCopy(), modelCopy.getDimensions());
    newModelCopy.passTurn(false);
    System.out.println("This color: " + tokenColor);
    newModelCopy.placeCurrentPlayerPiece(opponentMove);
    return newModelCopy.getScore(getOtherColor(tokenColor)) - newModelCopy.getScore(tokenColor);
  }

  // gets the moves of the opponent color player
  private List<ICell> opponentMoves(IReversiModel modelCopy) {
    TokenColor otherTokenColor = getOtherColor(tokenColor);
    List<Strategy> opponentStrats = Arrays.asList(
            new AvoidEdgesStrat(otherTokenColor),
            new ChooseCornersStrat(otherTokenColor),
            new GreedyStrat(otherTokenColor)
    );
    // Changed to pass in otherColor instead of color
    return new SandwichStrat(otherTokenColor, opponentStrats)
            .chooseMove(modelCopy, modelCopy.getValidMoves(otherTokenColor));
  }

  // returns whether strategy player has won the game
  private boolean playerWonGame(IReversiModel modelCopy) {
    return modelCopy.isGameOver() && modelCopy.getWinner()
            .filter(winner -> winner.equals(tokenColor))
            .isPresent();
  }

  // selects the move for strategy player to make that leaves opponent with the worst best move.
  private List<ICell> getOptimalMove(HashMap<ICell, Integer> moveScores, List<ICell> choices) {
    return moveScores.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .map(Collections::singletonList)
            .orElse(choices);
  }

  // gets the other color
  private TokenColor getOtherColor(TokenColor tokenColor) {
    return tokenColor == TokenColor.BLACK ? TokenColor.WHITE : TokenColor.BLACK;
  }
}