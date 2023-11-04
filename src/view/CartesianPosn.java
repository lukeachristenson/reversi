package view;

import model.HexagonCell;
import model.ICell;

public class CartesianPosn {
  private final double x;
  private final double y;
  private final double sideLength;

  public CartesianPosn(double x, double y, double sideLength) {
    this.x = x;
    this.y = y;
    this.sideLength = sideLength;
  }

  /**
   * Gets the CartesianPosn from the given ICell.
   * @param cell  the ICell to get the CartesianPosn from
   * @return    the CartesianPosn
   */
  public CartesianPosn getFromICell(ICell cell) {
    // If q = 0
    double central = this.sideLength * Math.sin(Math.PI / 3);
    double linear = this.sideLength;
    double x = 0;
    double y = 0;
    double sideLength = this.sideLength;
    if(cell.getCoordinates().get(0) == 0) {
      x = 2 * central * cell.getCoordinates().get(1) * Math.cos(Math.PI / 3);
      y = -x;
    } else if(cell.getCoordinates().get(1) == 0) {
      x = 2 * linear * cell.getCoordinates().get(0);
      y = 0;
    } else if(cell.getCoordinates().get(2) == 0) {
      x = 2 * central * cell.getCoordinates().get(0) * Math.cos(Math.PI / 3);
      y = x;
    } else {
      //TODO: Implement this using the correct proportions.
      x = 0;
      y = 0;
    }

    return new CartesianPosn(x, y, sideLength);
  }

  /**
   * Gets the ICell from the given CartesianPosn.
   * @param posn  the CartesianPosn to get the ICell from
   * @return    the ICell
   */
  public static ICell getICellFromCartesianPosn(CartesianPosn posn) {
    // TODO: Implement this method using the correct proportions.

    return null;
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public double getSideLength() {
    return this.sideLength;
  }
}
