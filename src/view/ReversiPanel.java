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
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 1000);
  }


  /**
   * TODO REWRITE THIS JAVADOC
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

    Map<CartesianPosn, Optional<model.Color>> drawMap = new HashMap<>();
    IBoard boardCopy = this.roModel.createBoardCopy();

    for (ICell cell : boardCopy.getPositionsMapCopy().keySet()) {
      drawMap.put(CartesianPosn.getFromICell(cell), boardCopy.getCellOccupant(cell));
    }

    int hexSize = 30; // Adjust the size as needed

    for (CartesianPosn posn : drawMap.keySet()) {
      int centerX = posn.getX() * hexSize * 3;
      int centerY = posn.getY() * hexSize * 2;

      // Offset Y for odd rows
      if (posn.getX() % 2 == 1) {
        centerY += hexSize * Math.sqrt(3);
      }

      g2d.setColor(Color.BLACK); // Default color

      if (drawMap.get(posn).isPresent()) {
        if (drawMap.get(posn).get() == model.Color.BLACK) {
          g2d.setColor(Color.BLACK);
        } else if (drawMap.get(posn).get() == model.Color.WHITE) {
          g2d.setColor(Color.WHITE);
        }
      }

      drawHexagon(g2d, centerX, centerY, hexSize);
    }
  }

  // ... (other methods)

  private void drawHexagon(Graphics2D g, int centerX, int centerY, int size) {
    Path2D path = new Path2D.Double();
    double angle = Math.PI / 3; // Angle between hexagon vertices

    for (int i = 0; i < 6; i++) {
      double x = centerX + size * Math.cos(i * angle);
      double y = centerY + size * Math.sin(i * angle);

      if (i == 0) {
        path.moveTo(x, y);
      } else {
        path.lineTo(x, y);
      }
    }

    path.closePath();

    g.fill(path);
  }

}



