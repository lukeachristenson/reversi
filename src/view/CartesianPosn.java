package view;

import model.HexagonCell;
import model.ICell;

public class CartesianPosn {
  private final int x;
  private final int y;

  public CartesianPosn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the CartesianPosn from the given ICell.
   * @param cell  the ICell to get the CartesianPosn from
   * @return    the CartesianPosn
   */
  public static CartesianPosn getFromICell(ICell cell) {
    return new CartesianPosn(cell.getCoordinates().get(0), cell.getCoordinates().get(1));
  }

  /**
   * Gets the ICell from the given CartesianPosn.
   * @param posn  the CartesianPosn to get the ICell from
   * @return    the ICell
   */
  public static ICell getICellFromCartesianPosn(CartesianPosn posn) {
    return new HexagonCell(posn.getX(), posn.getY(), -posn.getX() - posn.getY());
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }
}
