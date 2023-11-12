package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class represents a hexagonal board for the game of HexReversi.
 */
public class HexagonBoard implements IBoard {
  private final HashMap<ICell, Optional<Color>> boardPositions;
  private final int numRings;

  /**
   * Constructor for a HexagonBoard. Takes in a side length for the board.
   * @param numRings the number of rings of hexagons apart from the center one in the board.
   */
  public HexagonBoard(int numRings) {
    // Throw an exception if the side length is less than 3 because the board will be too small
    // to play a game.
    if (numRings < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    }
    this.boardPositions = new HashMap<>();
    this.numRings = numRings;
  }

  @Override
  public void newCellOwner(ICell cell, Optional<Color> color) {
    // Throws an exception if the cell is null.
    checkCellNotNull(cell);
    checkCellInBounds(cell);
    this.boardPositions.put(cell, color);
  }

  private void checkCellNotNull(ICell cell) {
    if (cell == null) {
      throw new IllegalArgumentException("Null cell passed into this method.");
    }
  }

  private void checkCellInBounds(ICell cell) {
    if (Math.abs(cell.getCoordinates().get(0)) >= numRings
            || Math.abs(cell.getCoordinates().get(1)) >= numRings
            || Math.abs(cell.getCoordinates().get(2)) >= numRings) {
      throw new IllegalArgumentException("Invalid coordinates for the target cell, " +
              "coordinates out of bounds");
    }
  }

  @Override
  public Optional<Color> getCellOccupant(ICell hexagonCell) throws IllegalArgumentException {
    checkCellNotNull(hexagonCell);
    checkCellInBounds(hexagonCell);
    return this.boardPositions.get(hexagonCell);
  }

  @Override
  public boolean validMove(ICell cell, Color colorToAdd, boolean flip) {
    // Throw an exception if the coordinates are out of bounds of the hexagonal grid.
    checkCellNotNull(cell);
    checkCellInBounds(cell);
    // Throw an exception if the specified cell is occupied by a non-empty player already.
    if (this.boardPositions.get(cell).isPresent()) {
      throw new IllegalStateException("Cell is already occupied.");
    }

    // Throw an exception if the sum of coordinates is not 0.
    // ************** Note: Handled in the constructor of HexagonCell. **************

    /*     Defined direction vectors for the six possible directions when placing a piece
     *      (1, -1, 0) -> Up 1, Right 1
     *      (-1, 1, 0) -> Down 1, Left 1
     *      (0, -1, 1) -> Up 1, Left 1
     *      (0, 1, -1) -> Down 1, Right 1
     *      (-1, 0, 1) -> Left 1
     *      (1, 0, -1) -> Right 1
     */
    // difference in q direction
    int[] dq = {1, -1, 0, 0, -1, 1};
    // difference in r direction
    int[] dr = {-1, 1, -1, 1, 0, 0};
    // difference in s direction
    int[] ds = {0, 0, 1, -1, 1, -1};


    int coordinateQ = cell.getCoordinates().get(0);
    int coordinateR = cell.getCoordinates().get(1);
    int coordinateS = cell.getCoordinates().get(2);

    List<ICell> cellsFlip = new ArrayList<>();

    // Iterate through all the directions.
    for (int direction = 0; direction < 6; direction++) {
      int qChange = dq[direction];
      int rChange = dr[direction];
      int sChange = ds[direction];
      List<ICell> cellsFlipTemp = new ArrayList<>();

      int targetQ = coordinateQ;
      int targetR = coordinateR;
      int targetS = coordinateS;
      targetQ += qChange;
      targetR += rChange;
      targetS += sChange;
      boolean foundOppositeColor = false;

      // While the target cell is in bounds of the board.
      while (Math.abs(targetQ) < numRings
              && Math.abs(targetR) < numRings
              && Math.abs(targetS) < numRings) {
        ICell targetCell = new HexagonCell(targetQ, targetR, targetS);

        // If the cell is occupied by an empty, return false
        if (boardPositions.get(targetCell).isEmpty()) {
          break;
        }

        // If the cell is occupied by the opposite player, set foundOppositeColor to true.
        if (!boardPositions.get(targetCell).equals(Optional.of(colorToAdd))) {
          cellsFlipTemp.add(targetCell);
          foundOppositeColor = true;
        }

        // If the cell is occupied by the same player, return true if foundOppositeColor is true.
        if (boardPositions.get(targetCell).equals(Optional.of(colorToAdd))) {
          if (foundOppositeColor) {
            cellsFlip.addAll(cellsFlipTemp);
          }
        }
        targetQ += qChange;
        targetR += rChange;
        targetS += sChange;
      }
    }
    if (flip) {
      flipMechanism(cellsFlip, colorToAdd);
    }
    return !cellsFlip.isEmpty();
  }

  //helper method to flip the cells
  private void flipMechanism(List<ICell> cellsToBeFlipped, Color colorToAdd) {
    for (ICell cell : cellsToBeFlipped) {
      this.newCellOwner(cell, Optional.of(colorToAdd));
    }
  }

  @Override
  public String toString() {
    int rows = 2 * numRings;
    int columns = 2 * numRings;

    // Create a 2D array to represent the board
    String[][] boardArray = new String[rows][columns];

    // Initialize the array with " " for empty cells
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        boardArray[i][j] = " ";
      }
    }

    // Populate the board with player symbols or "X" based on the cell state
    for (ICell cell : boardPositions.keySet()) {
      Optional<Color> occupant = boardPositions.get(cell);
      List<Integer> coordinates = cell.getCoordinates();
      int x = coordinates.get(0) + numRings; //q
      int y = coordinates.get(1) + numRings; //r
      boardArray[y][x] = occupant.map(Color::toString).orElse("-");
    }

    // Adjusts the spacing for every line in the board
    for (int i = 0; i < rows; i++) {
      for (int j = i; j > 0; j--) {
        boardArray[i][0] = boardArray[i][0] + " ";
      }
    }

    // Build the string representation of the board
    StringBuilder boardString = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        boardString.append(boardArray[i][j]).append(" ");
      }
      boardString.append("\n");
    }


    return boardString.toString();
  }

  @Override
  public int getColorCount(Color color) {
    int score = 0;
    // Iterate through all the cells in the board
    for (ICell cell : this.boardPositions.keySet()) {
      // If the cell is occupied by the player, increment the score
      if (this.boardPositions.get(cell).equals(Optional.of(color))) {
        score++;
      }
    }
    return score;
  }

  @Override
  public List<ICell> validMovesLeft(Color colorToAdd) {
    // Create a list to store the valid moves
    List<ICell> validMoves = new ArrayList<>();
    // Iterate through all the cells in the board
    for (ICell cell : this.boardPositions.keySet()) {
      // If the move is a valid move, for the playerToAdd, add that cell to the return list
      if (this.getCellOccupant(cell).isEmpty()) {
        if (validMove(cell, colorToAdd, false)) {
          validMoves.add(cell);
        }
      }
    }
    // Return the list of valid moves
    return validMoves;
  }

  @Override
  public Map<ICell, Optional<Color>> getPositionsMapCopy() {
    Map<ICell, Optional<Color>> mapCopy = new HashMap<>();
    for(ICell cell : this.boardPositions.keySet()) {
      ICell addCell = new HexagonCell(cell.getCoordinates().get(0), cell.getCoordinates().get(1),
              cell.getCoordinates().get(2));
      mapCopy.put(addCell, this.boardPositions.get(cell));
    }
    return Collections.unmodifiableMap(mapCopy);
  }

  @Override
  public int getNumRings() {
    return numRings;
  }

  @Override
  public Iterator<ICell> iterator() {
    return null;
  }
}
