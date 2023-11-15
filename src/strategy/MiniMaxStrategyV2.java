package strategy;

import model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents a strategy for the game of HexagonReversi. This strategy will choose the
 * move.
 */
public class MiniMaxStrategyV2 implements Strategy {
  private final Color color;

  /**
   * Constructor for a MiniMaxStrategyV2. Takes in a color for the player.
   * @param color the color of the player.
   */
  public MiniMaxStrategyV2(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    List<ICell> choices = getChoices(model, filteredMoves);
    HashMap<ICell, Integer> moveScores = evaluateMoves(model, choices);

    return getOptimalMove(moveScores, choices);
  }

  private List<ICell> getChoices(ROModel model, List<ICell> filteredMoves) {
    return filteredMoves.isEmpty() ? model.createBoardCopy().validMovesLeft(color) : filteredMoves;
  }

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

  private IReversiModel createModelCopyWithMove(ROModel model, ICell cell) {
    IBoard board = model.createBoardCopy();
    IReversiModel modelCopy = new HexagonReversi(board, model.getDimensions());
    modelCopy.placeCurrentPlayerPiece(cell);
    return modelCopy;
  }

  private int getOpponentHighestScore(IReversiModel modelCopy) {
    List<ICell> opponentResponses = opponentMoves(modelCopy);
    return opponentResponses.stream()
            .mapToInt(response -> evaluateOpponentMove(modelCopy, response))
            .max()
            .orElse(0);
  }

  private int evaluateOpponentMove(IReversiModel modelCopy, ICell opponentMove) {
    IReversiModel newModelCopy = new HexagonReversi(modelCopy.createBoardCopy(), modelCopy.getDimensions());
    newModelCopy.passTurn(false);
    System.out.println("This color: " + color);
//    System.out.println("Opponent move: " + opponentMove.getCoordinates());
    newModelCopy.placeCurrentPlayerPiece(opponentMove);
    return newModelCopy.getScore(getOtherColor(color)) - newModelCopy.getScore(color);
  }

  private List<ICell> opponentMoves(IReversiModel modelCopy) {
    Color otherColor = getOtherColor(color);
    List<Strategy> opponentStrats = Arrays.asList(
            new AvoidEdges(otherColor),
            new ChooseCorners(otherColor),
            new GreedyStrat(otherColor)
    );
    // Changed to pass in otherColor instead of color
    return new Sandwich(otherColor, opponentStrats)
            .chooseMove(modelCopy, modelCopy.getValidMoves(otherColor));
  }

  private boolean playerWonGame(IReversiModel modelCopy) {
    return modelCopy.isGameOver() && modelCopy.getWinner()
            .filter(winner -> winner.equals(color))
            .isPresent();
  }

  private List<ICell> getOptimalMove(HashMap<ICell, Integer> moveScores, List<ICell> choices) {
    return moveScores.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .map(Collections::singletonList)
            .orElse(choices);
  }

  private Color getOtherColor(Color color) {
    return color == Color.BLACK ? Color.WHITE : Color.BLACK;
  }
}