package strategy;

import java.util.ArrayList;
import java.util.List;

import model.Color;
import model.HexagonCell;
import model.ICell;
import model.ROModel;

public class AvoidEdges implements Strategy {
  private final model.Color color;

  public AvoidEdges(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    List<ICell> choices = (filteredMoves.isEmpty()) ? model.createBoardCopy().validMovesLeft(color) : filteredMoves;
    List<ICell> retList = new ArrayList<>(choices);
    List<ICell> corners = new ArrayList<>(new ChooseCorners(color).chooseMove(model, choices));

    // difference in q direction
    int[] dq = {1, -1, 0, 0, -1, 1};
    // difference in r direction
    int[] dr = {-1, 1, -1, 1, 0, 0};
    // difference in s direction
    int[] ds = {0, 0, 1, -1, 1, -1};
    System.out.println("Corners Here: " + corners);

   for(ICell corner : corners) {
      for (int i = 0; i < 6; i++) {
        int q = corner.getCoordinates().get(0) + dq[i];
        int r = corner.getCoordinates().get(1) + dr[i];
        int s = corner.getCoordinates().get(2) + ds[i];
        ICell cell = new HexagonCell(q, r, s);
        retList.remove(cell);
        System.out.println("Removed: " + cell.getCoordinates().toString());
      }
   }

    return retList;
  }
}
