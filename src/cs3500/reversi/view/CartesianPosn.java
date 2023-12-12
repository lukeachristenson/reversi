package cs3500.reversi.view;

import java.util.Objects;

import cs3500.reversi.model.ICell;

/**
 * This class represents a Cartesian position.

 */
public class CartesianPosn {
  private final double x;
  private final double y;
  private final double sideLength;

  /**
   * Constructor for a CartesianPosn. Takes in an x and y coordinate and a side length.
   * @param x the x coordinate
   * @param y the y coordinate
   * @param sideLength the side length
   */
  public CartesianPosn(double x, double y, double sideLength) {
    this.x = x;
    this.y = y;
    this.sideLength = sideLength;
  }

  /**
   * Gets the CartesianPosn from the given HexagonCell.
   *
   * @param cell the ICell to get the CartesianPosn from
   * @return the CartesianPosn
   */
  public CartesianPosn getFromHexCell(ICell cell) {
    double sideLength = this.sideLength;
    double x = sideLength * (Math.sqrt(3) * cell.getCoordinates().get(0)
            + Math.sqrt(3) / 2 * cell.getCoordinates().get(1));
    double y = -sideLength * (3.) / 2 * cell.getCoordinates().get(1);

    return new CartesianPosn(x, y, sideLength);
  }

  /**
   * Gets the CartesianPosn from the given HexagonCell.
   *
   * @param cell the ICell to get the CartesianPosn from
   * @return the CartesianPosn
   */
  public CartesianPosn getFromSquareCell(ICell cell) {
    double sideLength = this.sideLength;
    double x = + sideLength * cell.getCoordinates().get(0);
    double y = + sideLength * cell.getCoordinates().get(1);

    int xScale = (x < 0) ? -1 : 1;
    int yScale = (y < 0) ? -1 : 1;
    x = Math.abs(x) - sideLength/2;
    y = Math.abs(y) -  sideLength/2;

    return new CartesianPosn(x * xScale , y * yScale, sideLength);
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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CartesianPosn that = (CartesianPosn) o;
    return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  /**
   * Determines if the given CartesianPosn is within this CartesianPosn's cell.
   *
   * @param cellPosn the CartesianPosn to check if it is within the given cell
   * @return true if the given CartesianPosn is within the given cell, false otherwise
   */
  public boolean isWithinCell(CartesianPosn cellPosn) {
    // Write a method to check if the given CartesianPosn is within the given cell

    // Checks horizontal limits
    if (Math.abs(cellPosn.getX() - this.x) > this.sideLength * Math.sin(Math.PI / 3)) {
      return false;
    }

    // Checks vertical limits
    if (Math.abs(cellPosn.getY() - this.y) > this.sideLength) {
      return false;
    }

    return ((Math.abs(cellPosn.getY() - this.y))
            + (Math.abs(cellPosn.getX() - this.x)) / Math.sqrt(3) <= this.sideLength);
  }
}