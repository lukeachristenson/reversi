package cs3500.provider.view;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Objects;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import cs3500.provider.controller.ControllerFeatures;
import cs3500.provider.model.Cell;
import cs3500.provider.model.CubicCoordinate;
import cs3500.provider.model.ReadonlyReversiModel;

/**
 * A GUI view for the game of Reversi, allowing users to click to play the game.
 * Should not be able to affect the model directly.
 */
public class GUI extends JPanel implements ViewInterface {
  ReadonlyReversiModel model;
  ControllerFeatures controller;

  //private int boardSize; //need this?
  private int hexRadius; // Hexagon radius

  private CubicCoordinate selectedHexagon;

  //private ActionListener listener;

  /**
   * Default constructor for GUI that takes in model.
   * @param model model for the game.
   */
  public GUI(ReadonlyReversiModel model) {
    this.model = Objects.requireNonNull(model); //cannot modify the view with readonly
    //this.boardSize = model.getSideLength() * 2 - 1;

    this.hexRadius = 30; // Adjust as needed

    // Enable keyboard input for the JPanel
    setFocusable(true);
    requestFocusInWindow();

    //listener
    ReversiMouseListener listener = new ReversiMouseListener();
    this.addMouseListener(listener);
    ReversiKeyListener keyListener = new ReversiKeyListener();
    this.addKeyListener(keyListener);

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    // transform coord system?


    // Set the background color to blue
    Color customColor = new Color(52, 52, 52);
    //maybe make light or dark mode?
    setBackground(customColor);


    //placeholder code:
    Rectangle bounds = this.getBounds();


    int screenCenterX = bounds.width / 2;
    int screenCenterY = bounds.height / 2;

    //drawHexagon(g2d, screenCenterX, screenCenterY, hexRadius);
    drawHexagonBoard(g2d, screenCenterX, screenCenterY);

  }

  @Override
  public void addFeatures(ControllerFeatures features) {
    this.controller = features;
  }

  @Override
  public void display() {
    repaint();
  }


  private void drawHexagonBoard(Graphics g, int centerX, int centerY) {
    for (CubicCoordinate cubicCoord : model.getBoard().keySet()) {

      // Calculate screen coordinates based on the cubic coordinate offsets
      int horizontalSpacing = (int) (Math.sqrt(3) * hexRadius);
      int verticalSpacing = (int) (1.5 * hexRadius);

      int screenX = (int) (centerX + hexRadius * (Math.sqrt(3) * cubicCoord.getX() +
              Math.sqrt(3) / 2 * cubicCoord.getZ()));
      int screenY = centerY + (verticalSpacing * cubicCoord.getZ());

      drawHexagon(g, screenX, screenY, hexRadius, Color.GRAY);

      if (model.getBoard().get(cubicCoord).getOwner() == 1) {
        drawCircle(g, screenX, screenY, hexRadius * 2 / 3, Color.BLACK);
      }
      else if (model.getBoard().get(cubicCoord).getOwner() == 2) {
        drawCircle(g, screenX, screenY, hexRadius * 2 / 3, Color.WHITE);
      }

    }
  }

  private void drawCircle(Graphics g, int x, int y, int radius, Color color) {
    g.setColor(color);
    g.fillOval(x - (radius / 2), y - (radius / 2), radius, radius);
  }

  private void drawHexagon(Graphics g, int x, int y, int hexRadius, Color color) {
    int[] xPoints = new int[6];
    int[] yPoints = new int[6];

    for (int i = 0; i < 6; i++) {
      double angle = 2 * Math.PI / 6 * i - Math.PI / 2;
      xPoints[i] = (int) (x + hexRadius * Math.cos(angle));
      yPoints[i] = (int) (y + hexRadius * Math.sin(angle));
    }

    g.setColor(color); // Set the color of the hexagon
    g.fillPolygon(xPoints, yPoints, 6);

    g.setColor(Color.BLACK); // Set the color of the hexagon
    g.drawPolygon(xPoints, yPoints, 6);
  }


  // Helper function to check if a point is inside a hexagon
  private boolean isPointInHexagon(int x, int y, int hexCenterX, int hexCenterY, int hexRadius) {
    int[] xPoints = new int[6];
    int[] yPoints = new int[6];

    for (int i = 0; i < 6; i++) {
      double angle = 2 * Math.PI / 6 * i - Math.PI / 2;
      xPoints[i] = (int) (hexCenterX + hexRadius * Math.cos(angle));
      yPoints[i] = (int) (hexCenterY + hexRadius * Math.sin(angle));
    }

    Polygon hexagon = new Polygon(xPoints, yPoints, 6);

    return hexagon.contains(x, y);
  }


  /**
   * Finds the cubic coordinate of the hexagon that was clicked.
   * @param mouseX x coordinate of mouse click.
   * @param mouseY y coordinate of mouse click.
   * @return cubic coordinate of hexagon clicked.
   */
  public CubicCoordinate findCoordClicked(int mouseX, int mouseY) {
    for (CubicCoordinate coordinate: model.getBoard().keySet()) {
      // Calculate screen coordinates based on the cubic coordinate offsets
      int screenX = (int) (getWidth() / 2 + hexRadius * (Math.sqrt(3) * coordinate.getX() +
              Math.sqrt(3) / 2 * coordinate.getZ()));
      int screenY = getHeight() / 2 + (int) (1.5 * hexRadius * coordinate.getZ());

      if (isPointInHexagon(mouseX, mouseY, screenX, screenY, hexRadius)) {
        return coordinate;
      }
    }
    return null;
  }

  private class ReversiMouseListener extends MouseInputAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      int mouseX = e.getX();
      int mouseY = e.getY();
      Map<CubicCoordinate, Cell> board = model.getBoard();
      Graphics g = getGraphics();
      int verticalSpacing = (int) (1.5 * hexRadius);
      String message = "Mouse clicked at coordinates (" + mouseX + ", " + mouseY + ")";

      CubicCoordinate coordClicked = findCoordClicked(mouseX, mouseY);

      if (coordClicked != null) {
        int screenX = (int) (getWidth() / 2 + hexRadius * (Math.sqrt(3) * coordClicked.getX() +
                Math.sqrt(3) / 2 * coordClicked.getZ()));
        int screenY = getHeight() / 2 + (verticalSpacing * coordClicked.getZ());
        message += " in hexagon " + coordClicked;
        if (board.get(coordClicked).getOwner() == 0) {
          // Unhighlight the previously selected hexagon (if any)
          unhighlightHexagon(g, verticalSpacing);
          // deselects hexagon
          deselectOrSelectHexagon(coordClicked, g, screenX, screenY);
        }
      }
      else {
        // Unhighlights if clicked outside of board
        unhighlightHexagon(g, verticalSpacing);
      }
      //System.out.println(message);

    }



    private void deselectOrSelectHexagon(CubicCoordinate coordinate,
                                         Graphics g, int screenX, int screenY) {
      if (coordinate.equals(selectedHexagon)) {
        drawHexagon(g, screenX, screenY, hexRadius, Color.GRAY);
        //repaint();
        selectedHexagon = null;
      } else {
        // Highlight the newly selected hexagon
        drawHexagon(g, screenX, screenY, hexRadius, Color.CYAN);
        // Update the selectedHexagon to the current one
        selectedHexagon = coordinate;
      }

    }
  }

  private void unhighlightHexagon(Graphics g, int verticalSpacing) {
    if (selectedHexagon != null) {
      int prevScreenX = (int) (getWidth() / 2 + hexRadius * (Math.sqrt(3) *
              selectedHexagon.getX() + Math.sqrt(3) / 2 * selectedHexagon.getZ()));
      int prevScreenY = getHeight() / 2 + (verticalSpacing * selectedHexagon.getZ());
      drawHexagon(g, prevScreenX, prevScreenY, hexRadius, Color.GRAY);
    }

  }

  private class ReversiKeyListener extends KeyAdapter {

    @Override
    public void keyTyped(KeyEvent e) {
      if (e.getKeyChar() == 'p') {
        System.out.println("Pass attempted.");
        if (controller != null){
          controller.pass();
          selectedHexagon = null;
          repaint();
        }
      }

      if (e.getKeyChar() == 'm') {
        if (selectedHexagon == null) {
          System.out.println("Please select a hexagon before attempting to move.");
          return;
        }
        System.out.println("Move attempted at " + selectedHexagon + ".");
        if (controller != null){
          controller.move(selectedHexagon);
          selectedHexagon = null;
          repaint();
        }
      }
    }


  }

}