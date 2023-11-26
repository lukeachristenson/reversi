package cs3500.reversi.view;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import cs3500.reversi.model.ICell;

/**
 * This class contains static methods that are used to convert between different coordinate systems
 * and enable the view to interact with the board correctly.
 */
public class CoordUtilities {

  /**
   * Converts a hexagonal position to a cartesian position.
   * @param posn  the hexagonal position
   * @param cartesianPosnList the list of cartesian positions
   * @return  the cartesian position
   */
  public static CartesianPosn nearestCartPosn(CartesianPosn posn,
                                              List<CartesianPosn> cartesianPosnList) {
    double minDistance = Double.MAX_VALUE;
    CartesianPosn minPosn = null;
    for (CartesianPosn cartesianPosn : cartesianPosnList) {
      double distance = Math.sqrt(Math.pow(posn.getX() - cartesianPosn.getX(), 2)
              + Math.pow(posn.getY() - cartesianPosn.getY(), 2));
      if (distance < minDistance) {
        minDistance = distance;
        minPosn = cartesianPosn;
      }
    }
    return Objects.requireNonNull(minPosn);
  }

  /**
   * Converts a cartesian position to a hexagonal position.
   * @param posn  the cartesian position
   * @param cellToCartesianPosnMap  the map of cells to cartesian positions
   * @return  the hexagonal position
   */
  public static Optional<ICell> getCellFromCartesianPosn(Optional<CartesianPosn> posn, Map<ICell,
          CartesianPosn> cellToCartesianPosnMap) {
    if (posn.isPresent()) {
      for (ICell cell : cellToCartesianPosnMap.keySet()) {
        if (cellToCartesianPosnMap.get(cell).equals(posn.get())) {
          return Optional.of(cell);
        }
      }
    }
    return Optional.empty();
  }
}

