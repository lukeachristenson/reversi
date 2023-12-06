package cs3500.provider.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.provider.model.CubicCoordinate;
import cs3500.provider.model.ReadonlyReversiModel;


/**
 * This Reversi strategy will avoid playing next to corners.
 * If it must play next to a corner, capture max cells.
 */
public class AvoidCornerNeighborStrategy implements ReversiStrategy {

  @Override
  public CubicCoordinate chooseCoord(ReadonlyReversiModel model, int player) {

    List<CubicCoordinate> allNeighborsOfCorners = getAllNeighborsOfCorners(model);

    // if there is a valid move that is not a neighbor of a corner, play that
    for (CubicCoordinate coord : model.getBoard().keySet()) {
      if (model.isMoveValid(coord, player) && !allNeighborsOfCorners.contains(coord)) {
        return coord;
      }
    }

    //then it must choose a neighbor:
    return new CaptureMaxCellsThisMove().chooseCoord(model, player);


  }

  private List<CubicCoordinate> getAllNeighborsOfCorners(ReadonlyReversiModel model) {
    List<CubicCoordinate> allNeighborsOfCorners = new ArrayList<CubicCoordinate>();

    int maxCoordValue = model.getSideLength() - 1;

    for (CubicCoordinate coord : model.getBoard().keySet()) {

      if ((coord.getX() == maxCoordValue
              || coord.getY() == maxCoordValue
              || coord.getZ() == maxCoordValue) && (coord.getX() == 0
              || coord.getY() == 0
              || coord.getZ() == 0)) {
        allNeighborsOfCorners.addAll(coord.getNeighbors());
      }

    }
    return allNeighborsOfCorners;
  }


}
