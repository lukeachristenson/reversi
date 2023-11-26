package cs3500.reversi.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;

/**
 * This class represents a panel for the Reversi game.
 */
public class ReversiPanel extends JPanel {
  private final ROModel roModel;
  private final List<ViewFeatures> featureListeners;
  private final IBoard board;
  private final Map<ICell, CartesianPosn> cellToCartesianPosnMap;
  private final double scale;
  private final double sideLength;
  private final JLabel southLabel;
  private final cs3500.reversi.model.Color frameColor;
  private CartesianPosn mousePosn;
  private Optional<CartesianPosn> activeCell = Optional.empty();

  /**
   * Constructs a ReversiPanel with the given model and frameColor.
   *
   * @param roModel    the read only model to be used.
   * @param frameColor the color of the frame.
   */
  public ReversiPanel(ROModel roModel, cs3500.reversi.model.Color frameColor) {
    this.roModel = roModel;
    this.frameColor = frameColor;
    this.featureListeners = new ArrayList<>();
    this.scale = 0.75;
    this.sideLength = calculateSideLength(roModel.createBoardCopy());


    // Initialize listeners
    initializeListeners();

    // Initialize board and map
    this.board = roModel.createBoardCopy();
    this.cellToCartesianPosnMap = initializeCellToCartesianMap();

    // Initialize UI components
    setBackground(Color.darkGray);
    setLayout(new BorderLayout());
    this.southLabel = createLabel();
    JLabel northLabel = createLabel();
    add(southLabel, BorderLayout.SOUTH);
    add(northLabel, BorderLayout.NORTH);
  }

  // Initializes the mouse and keyboard listeners.
  private void initializeListeners() {
    MouseEventListener mouseEventListener = new MouseEventListener();
    this.addMouseListener(mouseEventListener);
    this.addMouseMotionListener(mouseEventListener);

    KeyboardEventListener keyboardEventListener = new KeyboardEventListener();
    this.addKeyListener(keyboardEventListener);
    this.setFocusable(true);
  }

  // Initializes the map of ICells to cartesian positions.
  private Map<ICell, CartesianPosn> initializeCellToCartesianMap() {
    Map<ICell, CartesianPosn> map = new HashMap<>();
    IBoard boardCopy = roModel.createBoardCopy();

    for (ICell icell : boardCopy.getPositionsMapCopy().keySet()) {
      CartesianPosn cartesianPosn = new CartesianPosn(0, 0, sideLength).getFromICell(icell);
      map.put(icell, cartesianPosn);
    }
    return map;
  }

  // Calculates the side length of the hexagons.
  private double calculateSideLength(IBoard boardCopy) {
    return scale * getPreferredLogicalSize().width / (2 * (boardCopy.getNumRings() + 1));
  }

  // Creates a label.
  private JLabel createLabel() {
    JLabel label = new JLabel("");
    label.setForeground(Color.white);
    label.setPreferredSize(new Dimension(100, 100));
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setFont(new Font(label.getFont().getName(), Font.BOLD, 30));
    return label;
  }

  /**
   * Returns the preferred physical size of the ReversiPanel.
   * This size is used to determine the panel's dimensions when displayed on the screen.
   *
   * @return A Dimension object representing the preferred physical size
   *         (width x height) of the panel.
   */

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 1000);
  }

  /**
   * Returns the preferred logical size of the ReversiPanel.
   * This size is used for calculations related to the game's logical layout,
   * such as cell positioning.
   *
   * @return A Dimension object representing the preferred logical size
   *         (width x height) of the panel.
   */

  private Dimension getPreferredLogicalSize() {
    return new Dimension(100, 100);
  }

  /**
   * Adds a ViewFeatures listener to the list of listeners.
   *
   * @param features the ViewFeatures listener to be added.
   */
  public void addFeaturesListener(ViewFeatures features) {
    this.featureListeners.add(Objects.requireNonNull(features));
  }

  /**
   * Displays the given message on the screen.
   */
  public void advance() {
    this.repaint();
    this.southLabel.repaint();
    if (roModel.isGameOver()) {
      activeCell = Optional.empty();
      String message = "Game Over! " + roModel.getWinner().get().toString() + " wins!";
      JOptionPane.showMessageDialog(this, message
              , "Game Over", JOptionPane.INFORMATION_MESSAGE);
      System.exit(0);
    }
  }

  /**
   * Displays an error message on the screen.
   */
  public void error() {
    JOptionPane.showMessageDialog(this, "Invalid Move"
            , "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
    activeCell = Optional.empty();
    System.err.println("Error");
    this.advance();
  }

  /**
   * Generates a transformation from the logical coordinate system to
   * the physical coordinate system.
   * This transformation is applied when rendering the game components onto the physical panel.
   *
   * @return An AffineTransform object representing the necessary transformation from logical to
   *         physical coordinates.
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.translate(getWidth() / 2., super.getHeight() / 2.);
    ret.scale(super.getWidth() / preferred.getWidth(), super.getHeight()
            / preferred.getHeight());
    ret.scale(1, -1);
    return ret;
  }

  /**
   * Generates a transformation from the physical coordinate system to the logical coordinate
   * system. This transformation is used when interpreting physical mouse events in terms of the
   * game's logical layout.
   *
   * @return An AffineTransform object representing the necessary transformation from physical to
   *         logical coordinates.
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(1, -1);
    ret.scale(preferred.getWidth() / super.getWidth(), preferred.getHeight()
            / super.getHeight());
    ret.translate(-super.getWidth() / 2., -super.getHeight() / 2.);
    return ret;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());

    Map<CartesianPosn, Optional<cs3500.reversi.model.Color>> drawMap = createDrawMap();
    drawHexagons(g2d, drawMap);
    placeTokensOnBoard(g2d, drawMap);
    highlightActiveCell(g2d, drawMap);
    Optional<ICell> chosenCell = CoordUtilities.getCellFromCartesianPosn(this.activeCell
            , Collections.unmodifiableMap(this.cellToCartesianPosnMap));
    chosenCell.ifPresent(iCell -> System.out.println("Highlighted Cell: " +
            iCell.getCoordinates()));
  }

  // Creates a map of cartesian positions to the color of the token at that position.
  private Map<CartesianPosn, Optional<cs3500.reversi.model.Color>> createDrawMap() {
    Map<CartesianPosn, Optional<cs3500.reversi.model.Color>> drawMap = new HashMap<>();
    IBoard boardCopy = this.roModel.createBoardCopy();
    for (ICell cell : this.cellToCartesianPosnMap.keySet()) {
      drawMap.put(this.cellToCartesianPosnMap.get(cell), boardCopy.getCellOccupant(cell));
    }
    return drawMap;
  }

  // Draws the hexagons on the board.
  private void drawHexagons(Graphics2D g2d, Map<CartesianPosn
          , Optional<cs3500.reversi.model.Color>> drawMap) {
    for (CartesianPosn posn : drawMap.keySet()) {
      g2d.setColor(Color.GRAY);
      drawHexagon(g2d, posn.getX(), posn.getY(), sideLength);
    }
  }

  // Places the tokens on the board.
  private void placeTokensOnBoard(Graphics2D g2d, Map<CartesianPosn
          , Optional<cs3500.reversi.model.Color>> drawMap) {
    for (CartesianPosn posn : drawMap.keySet()) {
      if (drawMap.get(posn).isPresent()) {
        Color tokenColor = drawMap.get(posn).get()
                == cs3500.reversi.model.Color.BLACK ? Color.BLACK : Color.WHITE;
        placeToken(g2d, posn, tokenColor);
      }
    }
  }

  // Places a token at the given cartesian position.
  private void placeToken(Graphics2D g2d, CartesianPosn posn, Color color) {
    g2d.setColor(color);
    double radius = sideLength * Math.sqrt(3) / 2 * 0.5;
    g2d.fill(new Ellipse2D.Double(
            posn.getX() - radius, posn.getY() - radius, 2 * radius, 2 * radius));
  }

  // Highlights the active cell.
  private void highlightActiveCell(Graphics2D g2d, Map<CartesianPosn
          , Optional<cs3500.reversi.model.Color>> drawMap) {
    if (activeCell.isPresent() && drawMap.get(activeCell.get()).isEmpty()) {
      g2d.setColor(Color.CYAN);
      CartesianPosn activePosn = activeCell.get();
      drawHexagon(g2d, activePosn.getX(), activePosn.getY(), sideLength);
    }
  }

  // Draws a hexagon at the given cartesian position.
  private void drawHexagon(Graphics2D g, double centerX, double centerY, double sideLength) {
    Path2D mainPath = new Path2D.Double();

    double leftX = centerX - sideLength * Math.sin(Math.PI / 3);
    double rightX = centerX + sideLength * Math.sin(Math.PI / 3);
    double angleDownY = centerY - sideLength * Math.cos(Math.PI / 3);
    double angleUpY = centerY + sideLength * Math.cos(Math.PI / 3);
    double upwardsY = centerY + sideLength * Math.cos(0.);
    double downwardsY = centerY - sideLength * Math.cos(0.);

    double[] xPoints = {leftX, leftX, centerX, rightX, rightX, centerX, leftX};
    double[] yPoints = {angleDownY, angleUpY, upwardsY, angleUpY, angleDownY
            , downwardsY, angleDownY};

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
    g.setStroke(new BasicStroke((float) sideLength * 0.06f));
    g.draw(mainPath);
  }

  // Returns the cartesian position of the given ICell.
  private class MouseEventListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      this.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      CartesianPosn nearestPosn = CoordUtilities.nearestCartPosn(ReversiPanel.this.mousePosn,
              new ArrayList<>(ReversiPanel.this.cellToCartesianPosnMap.values()));

      if (nearestPosn.isWithinCell(ReversiPanel.this.mousePosn)) {
        // If cell was clicked twice, removes the highlight, else, highlights the cell.
        ReversiPanel.this.activeCell
                = ReversiPanel.this.activeCell
                .equals(Optional.of(nearestPosn)) ? Optional.empty() : Optional.of(nearestPosn);

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
              scale * getPreferredLogicalSize().width / (2 * (board.getNumRings() + 1)));
    }
  }

  // Plays a move when the space bar is pressed and passes when the p key is pressed.
  private class KeyboardEventListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      // If the current player is the same as the frame color, then the player can make a move.
      if (ReversiPanel.this.roModel.getCurrentColor().equals(ReversiPanel.this.frameColor)) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          for (ViewFeatures listener : ReversiPanel.this.featureListeners) {
            ReversiPanel.this.activeCell.ifPresent(cartesianPosn
                -> listener.playMove(
                    CoordUtilities.getCellFromCartesianPosn(
                            ReversiPanel.this.activeCell,
                            Collections.unmodifiableMap(
                                    ReversiPanel.this.cellToCartesianPosnMap)).get()));
          }
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
          for (ViewFeatures listener : ReversiPanel.this.featureListeners) {
            listener.pass();
          }
        }
      }
      ReversiPanel.this.activeCell = Optional.empty();
      ReversiPanel.this.repaint();
    }
  }
}
