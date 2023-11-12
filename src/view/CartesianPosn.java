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
    double sideLength = this.sideLength;
    double x = sideLength * (Math.sqrt(3) * cell.getCoordinates().get(0)  +  Math.sqrt(3)/2 * cell.getCoordinates().get(1));
    double y = - sideLength * (3.)/2 * cell.getCoordinates().get(1);

    return new CartesianPosn(x, y, sideLength);
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

  /**
   * Determines if the given CartesianPosn is within this CartesianPosn's cell.
   * @param cellPosn  the CartesianPosn to check if it is within the given cell
   * @return  true if the given CartesianPosn is within the given cell, false otherwise
   */
  public boolean isWithinCell(CartesianPosn cellPosn) {
    // Write a method to check if the given CartesianPosn is within the given cell

    // Checks horizontal limits
    if(Math.abs(cellPosn.getX() - this.x) > this.sideLength * Math.sin(Math.PI / 3)) {
//      System.out.println("---------FALSE ONES-------");
//      System.out.println("X:" + cellPosn.getX() + " Y:" + cellPosn.getY());
//      System.out.println("this.x: " + this.x + " this.y: " + this.y);
//      System.out.println(Math.abs(cellPosn.getX()) - this.x);
//      System.out.println(this.sideLength);
//      System.out.println(this.sideLength * Math.sin(Math.PI / 3));
      return false;
    }

    // Checks vertical limits
    if(Math.abs(cellPosn.getY() - this.y) > this.sideLength) {
//      System.out.println("HERE2");
      return false;
    }


    // y = - x/rt(3) + this.sideLength
    if((Math.abs(cellPosn.getY() - this.y)) + (Math.abs(cellPosn.getX() - this.x)) / Math.sqrt(3)  > this.sideLength) {
//      System.out.println("HERE3");
      return false;
    }

//    System.out.println("---------TRUE ONES---------");
//    System.out.println("X:" + cellPosn.getX() + " Y:" + cellPosn.getY());
//    System.out.println("this.x: " + this.x + " this.y: " + this.y);
//    System.out.println(Math.abs(cellPosn.getX() - this.x));
//    System.out.println(this.sideLength);
//    System.out.println(this.sideLength * Math.sin(Math.PI / 3));
    return true;
  }
}