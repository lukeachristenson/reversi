package cs3500.reversi.view;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;

public class SquareReversiPanel extends AbstractPanel{
  /**
   * Constructs a ReversiPanel with the given model and frameColor.
   *
   * @param roModel         the read only model to be used.
   * @param frameTokenColor the color of the frame.
   */
  public SquareReversiPanel(ROModel roModel, TokenColor frameTokenColor) {
    super(roModel, frameTokenColor);
  }

  @Override
  // Initializes the map of ICells to cartesian positions.
  protected Map<ICell, CartesianPosn> initializeCellToCartesianMap() {
    Map<ICell, CartesianPosn> map = new HashMap<>();
    IBoard boardCopy = roModel.createBoardCopy();

    for (ICell icell : boardCopy.getPositionsMapCopy().keySet()) {
      CartesianPosn cartesianPosn = new CartesianPosn(0, 0, sideLength)
              .getFromSquareCell(icell);
      map.put(icell, cartesianPosn);
    }
    return map;
  }

  @Override
  // Calculates the side length of the suqare tiles.
  protected double calculateSideLength(IBoard boardCopy) {
    return scale * getPreferredLogicalSize().width / (2 * boardCopy.getNumRings());
  }

  @Override
  // Draws a square tile at the given cartesian position.
  protected void drawTile(Graphics2D g, double centerX, double centerY, double sideLength) {
    Path2D mainPath = new Path2D.Double();

    double leftX = centerX - sideLength / 2;
    double rightX = centerX + sideLength / 2;
    double upwardsY = centerY + sideLength / 2;
    double downwardsY = centerY - sideLength / 2;

    double[] xPoints = {rightX, rightX, leftX, leftX, rightX};
    double[] yPoints = {upwardsY, downwardsY, downwardsY, upwardsY, upwardsY};

    // Move the Path to the first position on the path.
    mainPath.moveTo(xPoints[0], yPoints[0]);

    // Draw a line to the next point in the hexagon.
    for (int i = 1; i < 5; i++) {
      mainPath.lineTo(xPoints[i], yPoints[i]);
    }
    mainPath.closePath();
    g.fill(mainPath);

    // Outline the hexagon.
    g.setColor(Color.BLACK);
    g.setStroke(new BasicStroke((float) sideLength * 0.06f));
    g.draw(mainPath);
  }

}
