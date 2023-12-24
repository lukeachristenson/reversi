package cs3500.reversi.view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.controller.IPlayerFeature;
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

  // Highlights the active cell.
  protected void highlightActiveCell(Graphics2D g2d, Map<CartesianPosn, Optional<TokenColor>> drawMap) {
    if (activeCell.isPresent() && drawMap.get(activeCell.get()).isEmpty()) {
      g2d.setColor(Color.CYAN);
      CartesianPosn activePosn = activeCell.get();
      drawTile(g2d, activePosn.getX(), activePosn.getY(), sideLength);

      int x = (int) activePosn.getX();
      int y = (int) activePosn.getY();

      // Draw centered text
      g2d.setColor(Color.BLACK); // Change text color as needed

      // Flip the text
      g2d.setFont(new Font(g2d.getFont().getName(), Font.BOLD, 3)); // Change font and size as needed

      // Calculate the position for centered text
      FontMetrics fontMetrics = g2d.getFontMetrics();

      ICell cell = CoordUtilities.getSquareCellFromCartesianPosn(this.activeCell
              , Collections.unmodifiableMap(this.cellToCartesianPosnMap)).get();

      int numFlipped = Math.max(roModel.cellsFlipped(cell, roModel.getCurrentColor()), 0);
      String text = Integer.toString(numFlipped);
      int textWidth = fontMetrics.stringWidth(text);
      int textHeight = fontMetrics.getAscent();
      int centerX = x + textWidth / 2;
      int centerY = - y + textHeight / 2;

      // Apply the transformation and draw the text.
      AffineTransform affineTransform = new AffineTransform();
      affineTransform.scale(1, -1);
      g2d.transform(affineTransform);
      g2d.drawString(text, centerX, centerY);
      g2d.setTransform(new AffineTransform());
    }
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

  // Plays a move when the space bar is pressed and passes when the p key is pressed.
  private class KeyboardEventListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      if (!SquareReversiPanel.this.frameTokenColor.equals(SquareReversiPanel.this.roModel.getCurrentColor())) {
        JOptionPane.showMessageDialog(SquareReversiPanel.this,
                "Playing out of turn", "Not your turn",
                JOptionPane.INFORMATION_MESSAGE);
      }

      SquareReversiPanel.this.southLabel.setText("Current player: " + TokenColor.WHITE.toString());
      // If the current player is the same as the frame color, then the player can make a move.
      if (SquareReversiPanel.this.roModel.getCurrentColor().equals(SquareReversiPanel.this.frameTokenColor)) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
          for (IPlayerFeature listener : SquareReversiPanel.this.featureListeners) {
            SquareReversiPanel.this.activeCell.ifPresent(cartesianPosn
                    -> listener.playMove(
                    CoordUtilities.getSquareCellFromCartesianPosn(
                            SquareReversiPanel.this.activeCell,
                            Collections.unmodifiableMap(
                                    SquareReversiPanel.this.cellToCartesianPosnMap)).get()));
          }
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
          for (IPlayerFeature listener : SquareReversiPanel.this.featureListeners) {
            listener.pass();
          }
        }
      }
      SquareReversiPanel.this.activeCell = Optional.empty();
      SquareReversiPanel.this.roModel.toString();
      SquareReversiPanel.this.repaint();
    }
  }
}
