package strategy;

import model.Color;
import model.HexagonReversi;
import model.IBoard;
import model.ICell;
import model.IReversiModel;
import model.ROModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class MiniMaxStrategyV2 implements Strategy {
  private final Color color;

  public MiniMaxStrategyV2(Color color) {
    this.color = color;
  }

  // We can implement only the first two layers for now and then see what to do
  // agreed, this is a start on that. it creates new models to actually play out the moves
  // do we instead want to do something closer to what kanav waws doing
  // Yes. The main chooseMove method has a hashmap where it will map each icell to an integer which is
  // the difference in scores after the opponents best move for that ICell.

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    // Initiate a hashMap to evaluate map each cell's score difference.
    HashMap<ICell, Integer> moveScores = new HashMap<>();

    // If the filtered moves is empty, we evaluate based on the valid moves left.
    List<ICell> choices = (filteredMoves.isEmpty()) ? model.createBoardCopy().validMovesLeft(color) : filteredMoves;

    // For each cell in the choices try the move
    Color otherColor = getOtherColor(color);
    for (ICell cell : choices) {
      // Make a move on the copy of the model.
      IBoard board = model.createBoardCopy();
      IReversiModel modelCopy = new HexagonReversi(board, model.getDimensions());
      modelCopy.placeCurrentPlayerPiece(cell);

      //store the board with the move made on it
      IBoard newBoard = modelCopy.createBoardCopy();
      //for every opponent response to the move, see what your score is
      List<Strategy> opponentStrats = new ArrayList<>();
      opponentStrats.add(new AvoidEdges(otherColor));
      opponentStrats.add(new ChooseCorners(otherColor));
      opponentStrats.add(new GreedyStrat(otherColor));

      List<ICell> opponentResponses =
              new Sandwich(color, opponentStrats)
                      .chooseMove(modelCopy, modelCopy.getValidMoves(otherColor));
      Integer OpponentHighestScore = 0;
      for (ICell otherCell : opponentResponses) {
        HashMap<ICell, Integer> curScores = new HashMap<>();
        ICell OpponentBestMove = opponentResponses.get(0);
        //make a model with current players move already made
        IReversiModel newModelCopy = new HexagonReversi(newBoard, model.getDimensions());
        //switching to opponent moves
        newModelCopy.passTurn(false);
        newModelCopy.placeCurrentPlayerPiece(otherCell);
        Integer currentScore = newModelCopy.getScore(otherColor) - newModelCopy.getScore(color);
        if (currentScore > OpponentHighestScore) {
          OpponentHighestScore = currentScore;
          OpponentBestMove = otherCell;
        }
//        curScores.put(cell, newModelCopy.getScore(color) - newModelCopy.getScore(otherColor));
      }
      moveScores.put(cell, OpponentHighestScore);
    }
    //need to get the key whose value is the lowest from the hashmap moveScores
    Optional<Map.Entry<ICell, Integer>> optimalEntry = moveScores.entrySet()
            .stream()
            .min(Map.Entry.comparingByValue());
    // Check if an optimal cell is present
    if (optimalEntry.isPresent()) {
      ICell optimalCell = optimalEntry.get().getKey();
      return new ArrayList<>(Collections.singletonList(optimalCell));
    } else {
      return choices;
    }
  }



  private Color getOtherColor(Color color) {
    if (color.equals(Color.BLACK)) {
      return Color.WHITE;
    }
    return Color.BLACK;
  }


}
