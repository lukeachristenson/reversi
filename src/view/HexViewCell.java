package view;

import model.HexagonCell;
import model.ICell;

public class HexViewCell {
  private final CartesianPosn posn;
  private final ICell hexCell;

  public HexViewCell(CartesianPosn posn, ICell hexCell) {
    this.posn = posn;
    this.hexCell = hexCell;
  }

  public CartesianPosn getPosn() {
    // return unmodifiable version of posn
    return new CartesianPosn(this.posn.getX(), this.posn.getY(), this.posn.getSideLength());
  }

  public ICell getHexCell() {
    // return unmodifiable version of hexCell
    return new HexagonCell(this.hexCell.getCoordinates().get(0),
            this.hexCell.getCoordinates().get(1),
            this.hexCell.getCoordinates().get(2));
  }
}
