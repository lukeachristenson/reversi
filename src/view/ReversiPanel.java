package view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
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
  private CartesianPosn mousePosn;
  private final Map<ICell, CartesianPosn> cellToCartesianPosnMap;
  private Optional<CartesianPosn> activeCell = Optional.empty();
  private final double scale;
  private double sideLength;
  private JLabel southLabel;


  public ReversiPanel(ROModel roModel) {
    this.roModel = roModel;
    this.featureListeners = new ArrayList<>();
    MouseEventListener mouseEventListener = new MouseEventListener();
    this.addMouseListener(mouseEventListener);
    this.addMouseMotionListener(mouseEventListener);
    KeyboardEventListener keyboardEventListener = new KeyboardEventListener();
    this.addKeyListener(keyboardEventListener);
    this.setFocusable(true);
    this.board = this.roModel.createBoardCopy();
    this.mousePosn = null;

    // Set the scale
    this.scale = 0.5;

    // Initialize and populate the cellToCartesianPosnMap.
    this.cellToCartesianPosnMap = new HashMap<>();
    IBoard boardCopy = this.roModel.createBoardCopy();
    sideLength = scale * this.getPreferredLogicalSize().width/(2*(boardCopy.getNumRings() + 1));
    for (ICell icell : boardCopy.getPositionsMapCopy().keySet()) {
      CartesianPosn cartesianPosn = new CartesianPosn(0, 0, sideLength).getFromICell(icell);
      cellToCartesianPosnMap.put(icell, cartesianPosn);
    }

    this.setBackground(Color.darkGray);

    setLayout(new BorderLayout());
    this.southLabel = new JLabel("Reversi");
    this.southLabel.setForeground(Color.white);
    this.southLabel.setPreferredSize(new Dimension(100, 100));
    this.southLabel.setHorizontalAlignment(SwingConstants.CENTER);
    Font labelFont = new Font(this.southLabel.getFont().getName(), Font.BOLD, 30);
    this.southLabel.setFont(labelFont);
    add(southLabel, BorderLayout.SOUTH);
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
    this.southLabel.repaint();
    if(roModel.isGameOver()) {
      activeCell = Optional.empty();
      String message = "Game Over! ";
      JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
      System.exit(0);
    }
  }

  public void error() {
    //TODO: Implement this method by displaying an error message.
    JOptionPane.showMessageDialog(this, "Invalid Move", "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
    activeCell = Optional.empty();
    System.err.println("Error");
    this.advance();
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

    // Add the cartesian positions mapped to their occupants to the drawMap.
    for (ICell cell : this.cellToCartesianPosnMap.keySet()) {
      drawMap.put(this.cellToCartesianPosnMap.get(cell), boardCopy.getCellOccupant(cell));
    }

    // For each cartesian position in the drawMap, draw the hexagon with a specified size.
    for (CartesianPosn posn : drawMap.keySet()) {
      g2d.setColor(Color.GRAY);
      this.drawHexagon(g2d, posn.getX(), posn.getY(), sideLength);
    }

    // Place circular tokens on the board.
    for (CartesianPosn posn : drawMap.keySet()) {
      if(drawMap.get(posn).isPresent() && drawMap.get(posn).get() == model.Color.BLACK) {
        g2d.setColor(Color.BLACK);
        this.placeTokens(g2d, posn.getX(), posn.getY(), sideLength * Math.sqrt(3) / 2 * scale);
      } else if(drawMap.get(posn).isPresent() && drawMap.get(posn).get() == model.Color.WHITE) {
        g2d.setColor(Color.WHITE);
        this.placeTokens(g2d, posn.getX(), posn.getY(), sideLength * Math.sqrt(3) / 2 * scale);
      }
    }

    // Highlight the cell if it is the active cell.
    if (activeCell.isPresent() && drawMap.get(activeCell.get()).isEmpty()) {
        g2d.setColor(Color.CYAN);
        this.drawHexagon(g2d, activeCell.get().getX(), activeCell.get().getY(), sideLength);
        // Display the current highlighted cell's cube coordinates.
        this.southLabel.setText("Current Cell: " + Objects.requireNonNull(getICell(activeCell.get())).getCoordinates().toString());
        this.southLabel.repaint();
    } else {
      this.southLabel.setText("No Cell Selected");
      this.southLabel.repaint();
    }

  }

  private ICell getICell(CartesianPosn posn) {
    for (ICell cell : this.cellToCartesianPosnMap.keySet()) {
      if (this.cellToCartesianPosnMap.get(cell).equals(posn)) {
        return cell;
      }
    }
    return null;
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

    // Move the Path to the first position on the path.
    mainPath.moveTo(xPoints[0], yPoints[0]);

    // Draw a line to the next point in the hexagon.
    for (int i = 1; i < 7; i++) {
      mainPath.lineTo(xPoints[i], yPoints[i]);
    }
    mainPath.closePath();
    g.fill(mainPath);

    // Outline the hexagon.
    g.setColor(Color.BLACK);
    g.setStroke(new BasicStroke((float)sideLength * 0.06f));
    g.draw(mainPath);
  }

  private void placeTokens(Graphics2D g, double centerX, double centerY, double radius) {
    g.fill(new Ellipse2D.Double(
            centerX - radius, centerY - radius, 2* radius, 2* radius));
  }

  private class MouseEventListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      this.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      CartesianPosn nearestPosn = new CartesianPosn(ReversiPanel.this.mousePosn.getX(),
              ReversiPanel.this.mousePosn.getY(), ReversiPanel.this.mousePosn.getSideLength())
              .nearestCartPosn(ReversiPanel.this.mousePosn, new ArrayList<>(cellToCartesianPosnMap.values()));

      if(nearestPosn.isWithinCell(ReversiPanel.this.mousePosn)) {
        // If cell was clicked twice, removes the highlight, else, highlights the cell.
        ReversiPanel.this.activeCell = ReversiPanel.this.activeCell.equals(Optional.of(nearestPosn)) ? Optional.empty() : Optional.of(nearestPosn);

      } else {
        ReversiPanel.this.activeCell = Optional.empty();
      }
      ReversiPanel.this.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      Point physicalPoint = e.getPoint();
      Point2D logicalPoint = transformPhysicalToLogical().transform(physicalPoint, null);
      ReversiPanel.this.mousePosn = new CartesianPosn(logicalPoint.getX(), logicalPoint.getY(),
              scale * getPreferredLogicalSize().width/(2*(board.getNumRings() + 1)));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

  }

  private class KeyboardEventListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        for (ViewFeatures listener : ReversiPanel.this.featureListeners) {
          if(ReversiPanel.this.activeCell.isPresent()){
            ReversiPanel.this.activeCell.ifPresent(listener::playMove);
            ReversiPanel.this.activeCell = Optional.empty();
          }
        }
      } else if (e.getKeyCode() == KeyEvent.VK_P) {
        for(ViewFeatures listener : ReversiPanel.this.featureListeners) {
          listener.passTurn();
        }
      }
    }
  }
}