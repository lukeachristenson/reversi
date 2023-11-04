package view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.swing.*;

import model.IBoard;
import model.ICell;
import model.IPlayer;
import model.ROModel;

public class ReversiPanel extends JPanel {
  private final ROModel roModel;
  private final List<ViewFeatures> featureListeners;
  private final IBoard board;
  private final boolean mouseIsDown;
  private IPlayer activePlayer;
  private CartesianPosn mousePosn;

  public ReversiPanel(ROModel roModel) {
    this.roModel = roModel;
    this.featureListeners = new ArrayList<>();
    this.board = this.roModel.createBoardCopy();
    this.activePlayer = this.roModel.getCurrentPlayer();
    this.mouseIsDown = false;
    this.mousePosn = null;
  }

  /**
   * TODO REWRITE THIS JAVADOC
   *
   * @return Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 1000);
  }


  /**
   * TODO REWRITE THIS JAVADOC
   *
   * @return Our preferred *logical* size.
   */
  private Dimension getPreferredLogicalSize() {
    return new Dimension(100, 100);
  }


  public void addFeaturesListener(ViewFeatures features) {
    this.featureListeners.add(Objects.requireNonNull(features));
  }


  public void advance() {
    //TODO: Implement this method by advancing to the next board state.
  }

  public void error() {
    //TODO: Implement this method by displaying an error message.
  }

  /**
   * TODO REWRITE THIS JAVADOC
   * <p>
   * This is the inverse of {@link ReversiPanel#transformPhysicalToLogical()}.
   *
   * @return The necessary transformation
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.translate(getWidth() / 2., getHeight() / 2.);
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    ret.scale(1, -1);
    return ret;
  }


  /**
   * TODO REWRITE THIS JAVADOC
   * <p>
   * This is the inverse of {@link ReversiPanel#transformLogicalToPhysical()}.
   *
   * @return The necessary transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(1, -1);
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    ret.translate(-getWidth() / 2., -getHeight() / 2.);
    return ret;
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());

    // Initialize a map for cartesian posns to colors.
    Map<CartesianPosn, Optional<model.Color>> drawMap = new HashMap<>();

    // Create a copy of the board.
    IBoard boardCopy = this.roModel.createBoardCopy();
    int screenCoordX = this.getPreferredLogicalSize().width;
    double sideLength = (double) 0.7 * screenCoordX/(2*(boardCopy.getNumRings() + 1));

    // Add the cartesian positions to the drawMap.
    for (ICell cell : boardCopy.getPositionsMapCopy().keySet()) {
      drawMap.put(new CartesianPosn(0,0,sideLength).getFromICell(cell), boardCopy.getCellOccupant(cell));
    }

    // For each cartesian position in the drawMap, draw the hexagon with a specified size.
    for (CartesianPosn posn : drawMap.keySet()) {
      if (drawMap.get(posn).isPresent()) {
        if (drawMap.get(posn).get().equals(model.Color.BLACK)) {

          g2d.setColor(Color.BLACK);
        } else {
          g2d.setColor(Color.WHITE);
        }
      } else {
        g2d.setColor(Color.GRAY);
      }

      // TODO: Change the size of the hexagon to be based on the size of the board.
      this.drawHexagon(g2d, posn.getX(), posn.getY(), sideLength);
    }
  }

  // ... (other methods)
  private void drawHexagon(Graphics2D g, double centerX, double centerY, double sideLength) {
    Path2D mainPath = new Path2D.Double();
    double leftX = centerX - sideLength * Math.sin(Math.PI / 3);
    double rightX = centerX + sideLength * Math.sin(Math.PI / 3);
    double angleDownY = centerY - sideLength * Math.cos(Math.PI / 3);
    double angleUpY = centerY + sideLength * Math.cos(Math.PI / 3);
    double upwardsY = centerY + sideLength * Math.cos(0.);
    double downwardsY = centerY - sideLength * Math.cos(0.);

    double[] xPoints = {
            leftX,
            leftX,
            centerX,
            rightX,
            rightX,
            centerX,
            leftX};
    double[] yPoints = {
            angleDownY,
            angleUpY,
            upwardsY,
            angleUpY,
            angleDownY,
            downwardsY,
            angleDownY
    };

    // Move the Path to the center of the hexagon.
    mainPath.moveTo(xPoints[0], yPoints[0]);


    // Draw a line to the next point in the hexagon.
    for (int i = 1; i < 7; i++) {
      mainPath.lineTo(xPoints[i], yPoints[i]);
    }
    mainPath.closePath();
    g.fill(mainPath);
  }

}




