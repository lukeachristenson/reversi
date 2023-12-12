package cs3500.reversi.view;

import java.awt.*;
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

import cs3500.reversi.controller.IPlayerFeature;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;

/**
 * This class represents a panel for the Reversi game.
 */
public class AbstractPanel extends JPanel {
  final ROModel roModel;
  final List<IPlayerFeature> featureListeners;
  private final IBoard board;
  final Map<ICell, CartesianPosn> cellToCartesianPosnMap;
  double scale;
  final double sideLength;
  final JLabel southLabel;
  final TokenColor frameTokenColor;
  CartesianPosn mousePosn;
  Optional<CartesianPosn> activeCell = Optional.empty();

  /**
   * Constructs a ReversiPanel with the given model and frameColor.
   *
   * @param roModel         the read only model to be used.
   * @param frameTokenColor the color of the frame.
   */
  public AbstractPanel(ROModel roModel, TokenColor frameTokenColor) {
    this.roModel = roModel;
    this.frameTokenColor = frameTokenColor;
    this.featureListeners = new ArrayList<>();
    this.scale = 0.65;
    this.sideLength = calculateSideLength(this.roModel.createBoardCopy());

    // Initialize listeners
    this.initializeListeners();

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
  protected void initializeListeners() {
    MouseEventListener mouseEventListener = new MouseEventListener();
    this.addMouseListener(mouseEventListener);
    this.addMouseMotionListener(mouseEventListener);

    KeyboardEventListener keyboardEventListener = new KeyboardEventListener();
    this.addKeyListener(keyboardEventListener);
    this.setFocusable(true);
  }

  // Initializes the map of ICells to cartesian positions.
  protected Map<ICell, CartesianPosn> initializeCellToCartesianMap() {
    Map<ICell, CartesianPosn> map = new HashMap<>();
    IBoard boardCopy = roModel.createBoardCopy();

    for (ICell icell : boardCopy.getPositionsMapCopy().keySet()) {
      CartesianPosn cartesianPosn = new CartesianPosn(0, 0, sideLength).getFromHexCell(icell);
      map.put(icell, cartesianPosn);
    }
    return map;
  }

  // Calculates the side length of the hexagons.
  protected double calculateSideLength(IBoard boardCopy) {
    return scale * getPreferredLogicalSize().width / (2 * (boardCopy.getNumRings() + 1));
  }

  // Creates a label.
  protected JLabel createLabel() {
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
   *          (width x height) of the panel.
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
   *      (width x height) of the panel.
   */

  protected Dimension getPreferredLogicalSize() {
    return new Dimension(100, 100);
  }

  /**
   * Adds a ViewFeatures listener to the list of listeners.
   *
   * @param features the ViewFeatures listener to be added.
   */
  public void addFeaturesListener(IPlayerFeature features) {
    this.featureListeners.add(Objects.requireNonNull(features));
  }

  /**
   * Displays the given message on the screen.
   */
  public void advance() {
    this.repaint();
    this.southLabel.setText("Current player: " + roModel.getCurrentColor().toString());
    this.southLabel.repaint();
    if (roModel.isGameOver()) {

      activeCell = Optional.empty();
      String winner = (roModel.getWinner().isPresent()) ? roModel.getWinner().get().toString().
              concat(" wins!") : " Stalemate";
      String message = "Game Over! " + winner;
      JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
      System.exit(0);
    }
  }

  /**
   * Displays an error message on the screen.
   */
  public void error(String error) {
    JOptionPane.showMessageDialog(this, error
            , "Error!", JOptionPane.INFORMATION_MESSAGE);
    activeCell = Optional.empty();
    System.err.println("Error in ReversiPanel:");
    this.advance();
  }

  /**
   * Generates a transformation from the logical coordinate system to
   * the physical coordinate system.
   * This transformation is applied when rendering the game components onto the physical panel.
   *
   * @return An AffineTransform object representing the necessary transformation from logical to
   *          physical coordinates.
   */
  protected AffineTransform transformLogicalToPhysical() {
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
   *          logical coordinates.
   */
  protected AffineTransform transformPhysicalToLogical() {
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

    Map<CartesianPosn, Optional<TokenColor>> drawMap = createDrawMap();
    this.drawTiles(g2d, drawMap);
    placeTokensOnBoard(g2d, drawMap);

    highlightActiveCell(g2d, drawMap);
  }

  // Creates a map of cartesian positions to the color of the token at that position.
  protected Map<CartesianPosn, Optional<TokenColor>> createDrawMap() {
    Map<CartesianPosn, Optional<TokenColor>> drawMap = new HashMap<>();
    IBoard boardCopy = this.roModel.createBoardCopy();
    for (ICell cell : this.cellToCartesianPosnMap.keySet()) {
      drawMap.put(this.cellToCartesianPosnMap.get(cell), boardCopy.getCellOccupant(cell));
    }
    return drawMap;
  }

  // Draws the hexagon tiles on the board.
  protected void drawTiles(Graphics2D g2d, Map<CartesianPosn
          , Optional<TokenColor>> drawMap) {
    for (CartesianPosn posn : drawMap.keySet()) {
      g2d.setColor(Color.GRAY);
      drawTile(g2d, posn.getX(), posn.getY(), sideLength);
    }
  }

  // Places the tokens on the board.
  protected void placeTokensOnBoard(Graphics2D g2d, Map<CartesianPosn
          , Optional<TokenColor>> drawMap) {
    for (CartesianPosn posn : drawMap.keySet()) {
      if (drawMap.get(posn).isPresent()) {
        Color tokenColor = drawMap.get(posn).get()
                == TokenColor.BLACK ? Color.BLACK : Color.WHITE;
        placeToken(g2d, posn, tokenColor);
      }
    }
  }

  // Places a token at the given cartesian position.
  protected void placeToken(Graphics2D g2d, CartesianPosn posn, Color color) {
    g2d.setColor(color);
    double radius = sideLength * Math.sqrt(3) / 2 * 0.5;
    g2d.fill(new Ellipse2D.Double(
            posn.getX() - radius, posn.getY() - radius, 2 * radius, 2 * radius));
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

      ICell cell = CoordUtilities.getHexCellFromCartesianPosn(this.activeCell
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

  // Draws a hexagon tile at the given cartesian position.
  protected void drawTile(Graphics2D g, double centerX, double centerY, double sideLength) {
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
  protected class MouseEventListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      this.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      CartesianPosn nearestPosn = CoordUtilities.nearestCartPosn(AbstractPanel.this.mousePosn,
              new ArrayList<>(AbstractPanel.this.cellToCartesianPosnMap.values()));

      if (AbstractPanel.this.roModel.getCurrentColor().equals(AbstractPanel.this.frameTokenColor)) {
        if (nearestPosn.isWithinCell(AbstractPanel.this.mousePosn)) {
          // If cell was clicked twice, removes the highlight, else, highlights the cell.
          AbstractPanel.this.activeCell
                  = AbstractPanel.this.activeCell
                  .equals(Optional.of(nearestPosn)) ? Optional.empty() : Optional.of(nearestPosn);

        } else {
          AbstractPanel.this.activeCell = Optional.empty();
        }
      }
      AbstractPanel.this.repaint();
    }


    @Override
    public void mouseDragged(MouseEvent e) {
      Point physicalPoint = e.getPoint();
      Point2D logicalPoint = transformPhysicalToLogical().transform(physicalPoint, null);
      AbstractPanel.this.mousePosn = new CartesianPosn(logicalPoint.getX(), logicalPoint.getY(),
              AbstractPanel.this.sideLength);
    }
  }

  // Plays a move when the space bar is pressed and passes when the p key is pressed.
  private class KeyboardEventListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      if (!AbstractPanel.this.frameTokenColor.equals(AbstractPanel.this.roModel.getCurrentColor())) {
        JOptionPane.showMessageDialog(AbstractPanel.this,
                "Playing out of turn", "Not your turn",
                JOptionPane.INFORMATION_MESSAGE);
      }

      AbstractPanel.this.southLabel.setText("Current player: " + TokenColor.WHITE.toString());
      // If the current player is the same as the frame color, then the player can make a move.
      if (AbstractPanel.this.roModel.getCurrentColor().equals(AbstractPanel.this.frameTokenColor)) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
          for (IPlayerFeature listener : AbstractPanel.this.featureListeners) {
            AbstractPanel.this.activeCell.ifPresent(cartesianPosn
                    -> listener.playMove(
                    CoordUtilities.getHexCellFromCartesianPosn(
                            AbstractPanel.this.activeCell,
                            Collections.unmodifiableMap(
                                    AbstractPanel.this.cellToCartesianPosnMap)).get()));
          }
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
          for (IPlayerFeature listener : AbstractPanel.this.featureListeners) {
            listener.pass();
          }
        }
      }
      AbstractPanel.this.activeCell = Optional.empty();
      AbstractPanel.this.repaint();
    }
  }
}
