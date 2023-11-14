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
 * Function object strategy that chooses
 */
public class AvoidEdges implements Strategy {
  private final model.Color color;

  public AvoidEdges(Color color) {
    this.color = color;
  }

  private List<ICell> everyCell(int sideLength) throws IllegalStateException {
    // rings + 1 = sideLength, includes the center ring here
    List<ICell> cells = new ArrayList<>();
    Integer rings = sideLength - 1;
    for (int q = -rings; q <= rings; q++) {
      int r1 = Math.max(-rings, -q - rings);
      int r2 = Math.min(rings, -q + rings);
      for (int r = r1; r <= r2; r++) {
        HexagonCell hp = new HexagonCell(q, r, -q - r);
        cells.add(hp);
      }
    }
    return cells;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    List<ICell> choices = (filteredMoves.isEmpty()) ? model.createBoardCopy().validMovesLeft(color) : filteredMoves;
    List<ICell> retList = new ArrayList<>(choices);
    List<ICell> entireBoard = this.everyCell(model.getDimensions());
    List<ICell> corners = new ArrayList<>(new ChooseCorners(color).chooseMove(model, entireBoard));


    // difference in q direction
    int[] dq = {1, -1, 0, 0, -1, 1};
    // difference in r direction
    int[] dr = {-1, 1, -1, 1, 0, 0};
    // difference in s direction
    int[] ds = {0, 0, 1, -1, 1, -1};
    System.out.println("Corners Here: " + corners);

   for(ICell corner : corners) {
      //six directions from every cell
      for (int i = 0; i < 6; i++) {
        int q = corner.getCoordinates().get(0) + dq[i];
        int r = corner.getCoordinates().get(1) + dr[i];
        int s = corner.getCoordinates().get(2) + ds[i];
        ICell cell = new HexagonCell(q, r, s);
        retList.remove(cell);
        System.out.println("Removed: " + cell.getCoordinates().toString());
      }
   }
   //if the only available moves were edgeMoves, brings back the option to play them.
   if (retList.isEmpty()) {
     return choices;
   }
   return retList;
  }
}
