package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import model.HexagonCell;
import model.IBoard;
import model.ICell;
import model.IPlayer;
import model.ROModel;

public class ReversiPanel extends JPanel {
  private final ROModel roModel;
  private final List<ViewFeatures> featureListeners;
  private final IBoard board;
  private boolean mouseIsDown;
  private IPlayer activePlayer;
  private CartesianPosn mousePosn;
  private CartesianPosn activeCell;
  private List<HexViewCell> cells;
  private List<CartesianPosn> cartesianPosns;
  private int clickCount;

  private final double scale = 0.5;

  public ReversiPanel(ROModel roModel) {
    this.roModel = roModel;
    this.featureListeners = new ArrayList<>();
    MouseEventListener mouseEventListener = new MouseEventListener();
    this.addMouseListener(mouseEventListener);
    this.addMouseMotionListener(mouseEventListener);
    this.board = this.roModel.createBoardCopy();
    this.activePlayer = this.roModel.getCurrentPlayer();
    this.mouseIsDown = false;
    this.mousePosn = null;
    cartesianPosns = new ArrayList<>();
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
    System.out.println("Advancing");
    this.repaint();
  }

  public void error() {
    //TODO: Implement this method by displaying an error message.
    JOptionPane.showMessageDialog(this, "Invalid Move", "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
    System.err.println("Error");
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
    // Set the sideLength of the board based on the number of rings and the screen size.
    double sideLength = (double) scale * screenCoordX/(2*(boardCopy.getNumRings() + 1));

    // Add the cartesian positions to the drawMap.
    for (ICell cell : boardCopy.getPositionsMapCopy().keySet()) {
      drawMap.put(new CartesianPosn(0,0,sideLength).getFromICell(cell), boardCopy.getCellOccupant(cell));
      cartesianPosns.add(new CartesianPosn(0,0,sideLength).getFromICell(cell));
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
      this.drawHexagon(g2d, posn.getX(), posn.getY(), sideLength);
    }
  }

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

  private void highLightCell(Graphics2D g, double centerX, double centerY, double sideLength) {
    Path2D mainPath = new Path2D.Double();
    double shortenedLength = sideLength * scale;
    double leftX = centerX - shortenedLength * Math.sin(Math.PI / 3);
    double rightX = centerX + shortenedLength * Math.sin(Math.PI / 3);
    double angleDownY = centerY - shortenedLength * Math.cos(Math.PI / 3);
    double angleUpY = centerY + shortenedLength * Math.cos(Math.PI / 3);
    double upwardsY = centerY + shortenedLength * Math.cos(0.);
    double downwardsY = centerY - shortenedLength * Math.cos(0.);

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

  private class MouseEventListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      ReversiPanel.this.mouseIsDown = true;
      this.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      ReversiPanel.this.mouseIsDown = false;
      CartesianPosn nearestPosn = new CartesianPosn(ReversiPanel.this.mousePosn.getX(),
              ReversiPanel.this.mousePosn.getY(), ReversiPanel.this.mousePosn.getSideLength())
              .nearestCartPosn(ReversiPanel.this.mousePosn, cartesianPosns);

      ReversiPanel.this.featureListeners.forEach(l -> l.playMove(nearestPosn));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      Point physicalPoint = e.getPoint();
      Point2D logicalPoint = transformPhysicalToLogical().transform(physicalPoint, null);
      System.out.println(physicalPoint);
      System.out.println(logicalPoint.toString());
      ReversiPanel.this.mousePosn = new CartesianPosn(logicalPoint.getX(), logicalPoint.getY(),
              scale * getPreferredLogicalSize().width/(2*(board.getNumRings() + 1)));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void highlightCell(CartesianPosn cellPosn) {
      // Your highlight logic goes here


      // You can modify the cell's state to indicate it's highlighted or change its appearance
      // For example, change its border color or fill color.
      // Ensure to repaint the panel after highlighting the cell to reflect the changes visually.
    }
  }
}