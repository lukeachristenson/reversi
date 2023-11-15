package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Color;
import model.HexagonBoard;
import model.HexagonCell;
import model.IBoard;
import model.ICell;
import model.ROModel;

/**
 * A class that represents a strategy that avoids edges(cells adjacent to corners).
 */
public class AvoidEdges implements Strategy {
  private final model.Color color;

  /**
   * Constructs a new AvoidEdges strategy.
   *
   * @param color the color of the player using this strategy.
   */
  public AvoidEdges(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    List<ICell> choices = (filteredMoves.isEmpty()) ? model.createBoardCopy().validMovesLeft(color) : filteredMoves;
    List<ICell> retList = new ArrayList<>(choices);
    List<ICell> entireBoard = new ArrayList<>(model.createBoardCopy().getPositionsMapCopy().keySet());
    List<ICell> corners = new ArrayList<>(new ChooseCorners(color).chooseMove(model, entireBoard));

    // difference in q direction
    int[] dq = {1, -1, 0, 0, -1, 1};
    // difference in r direction
    int[] dr = {-1, 1, -1, 1, 0, 0};
    // difference in s direction
    int[] ds = {0, 0, 1, -1, 1, -1};

    for (ICell corner : corners) {
      //six directions from every cell
      for (int i = 0; i < 6; i++) {
        int q = corner.getCoordinates().get(0) + dq[i];
        int r = corner.getCoordinates().get(1) + dr[i];
        int s = corner.getCoordinates().get(2) + ds[i];
        ICell cell = new HexagonCell(q, r, s);
        retList.remove(cell);
      }
    }
    //if the only available moves were edgeMoves, brings back the option to play them.
    if (retList.isEmpty()) {
      return choices;
    }
    return retList;
  }
}
