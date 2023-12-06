package cs3500.provider.strategy;


//loop through and pick the CubicCoordinate with coordinates
// where the sum of their absolute values are the highest
//and one of their coordinates is closest to zero

import cs3500.provider.model.CubicCoordinate;
import cs3500.provider.model.ReadonlyReversiModel;

/**
 * This Reversi strategy will try to go for the corners.
 */
public class GoForCornersStrategy implements ReversiStrategy {

  @Override
  public CubicCoordinate chooseCoord(ReadonlyReversiModel model, int player) {
    CubicCoordinate bestCoordinate = null;
    //sum of abs value of all coords of a CubicCoord
    int maxSum = 0;
    //value of
    int closestCoordToZero = model.getSideLength() - 1;

    for (CubicCoordinate coord : model.getBoard().keySet()) {
      if (model.isMoveValid(coord, player)) {
        int sum = Math.abs(coord.getX()) + Math.abs(coord.getY()) + Math.abs(coord.getZ());
        int coordClosestToZero = Math.min(Math.min(Math.abs(coord.getX()),
                Math.abs(coord.getY())),
                Math.abs(coord.getZ()));

        // moves towards outer ring or corners
        if (sum > maxSum || (sum == maxSum && coordClosestToZero < closestCoordToZero)) {
          maxSum = sum;
          closestCoordToZero = coordClosestToZero;
          bestCoordinate = coord;
        }
      }
    }

    //    if (new CaptureMaxCellsThisMove().chooseCoord(model, player).equals(bestCoordinate)) {
    //      return bestCoordinate;
    //    }
    return bestCoordinate;
  }

}
