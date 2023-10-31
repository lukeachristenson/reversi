package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a hexagonal board for the game of HexReversi.
 */
public class HexagonBoard implements IBoard {
  private final HashMap<ICell, Optional<Color>> boardPositions;
  private final int sideLength;

  public HexagonBoard(int sideLength) {
    this.boardPositions = new HashMap<>();
    this.sideLength = sideLength;
  }

  @Override
  public void newCellOwner(ICell cell, Optional<Color> color) {
    // Throws an exception if the cell is null.
    checkCellNotNull(cell);

    checkCellInBounds(cell);
    this.boardPositions.put(cell, color);
  }

  private static void checkCellNotNull(ICell cell) {
    if (cell == null) {
      throw new IllegalArgumentException("Null cell passed into newCellOwner.");
    }
  }

  private void checkCellInBounds(ICell cell) {
    if (!(Math.abs(cell.getCoordinates().get(0)) < sideLength)
            || !(Math.abs(cell.getCoordinates().get(1)) < sideLength)
            || !(Math.abs(cell.getCoordinates().get(2)) < sideLength)) {
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

    /**     Defined direction vectors for the six possible directions when placing a piece
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

      while (Math.abs(targetQ) < sideLength
              && Math.abs(targetR) < sideLength
              && Math.abs(targetS) < sideLength) {
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

  private void flipMechanism(List<ICell> cellsToBeFlipped, Color colorToAdd) {
    for (ICell cell : cellsToBeFlipped) {
      this.newCellOwner(cell, Optional.of(colorToAdd));
    }
  }

  @Override
  public String toString() {
    int rows = 2 * sideLength;
    int columns = 2 * sideLength;

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
      int x = coordinates.get(0) + sideLength; //q
      int y = coordinates.get(1) + sideLength; //r
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
  public int getScore(Color color) {
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
}
