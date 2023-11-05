package view;

import java.util.List;
import java.util.Objects;

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
    double x = 0;
    double y = 0;
    double sideLength = this.sideLength;

    if(cell.getCoordinates().get(0) == 0) {     // q = 0 works
      x = 2 * central * cell.getCoordinates().get(1) * Math.cos(Math.PI / 3);
      y = -2 * central * cell.getCoordinates().get(1) * Math.sin(Math.PI / 3);
    } else if(cell.getCoordinates().get(1) == 0) { // r = 0 works
      x = 2 * central * cell.getCoordinates().get(0);
      y = 0;
    } else if(cell.getCoordinates().get(2) == 0) { // s = 0 works
      x = 2 * central * cell.getCoordinates().get(0) * Math.cos(Math.PI / 3);
      y = 2 * central * cell.getCoordinates().get(0) * Math.sin(Math.PI / 3);
    } else {
      int q = cell.getCoordinates().get(0) + cell.getCoordinates().get(1);
      int r = 0;
      int s = cell.getCoordinates().get(2);
      ICell axisCell = new HexagonCell(q, r, s);
      ICell directionCell = new HexagonCell(cell.getCoordinates().get(0) - axisCell.getCoordinates().get(0),
              cell.getCoordinates().get(1) - axisCell.getCoordinates().get(1), 0);


      CartesianPosn axisPosn = this.getFromICell(axisCell);
      CartesianPosn directionPosn = this.getFromICell(directionCell);
      x = axisPosn.getX() + directionPosn.getX();
      y = axisPosn.getY() + directionPosn.getY();
    }
    return new CartesianPosn(x, y, sideLength);
  }

  /**
   * Gets the nearest CartesianPosition from the given CartesianPosns.
   * @param posn  the CartesianPosn to get the nearest CartesianPosn from
   *              the given CartesianPosns
   * @param cartesianPosnList   the list of CartesianPosns to get the nearest
   *                           CartesianPosn from
   * @return    the nearest CartesianPosn
   */
  public CartesianPosn nearestCartPosn(CartesianPosn posn, List<CartesianPosn> cartesianPosnList) {
    double minDistance = 10000.;
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

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public double getSideLength() {
    return this.sideLength;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartesianPosn that = (CartesianPosn) o;
    return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}