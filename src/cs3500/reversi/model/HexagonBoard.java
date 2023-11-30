package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class represents a hexagonal board for the game of HexagonReversi.
 */
public class HexagonBoard implements IBoard {
  private final HashMap<ICell, Optional<TokenColor>> boardPositions;
  private final int sideLength;

  /**
   * Constructor for a HexagonBoard. Takes in a side length for the board.
   *
   * @param sideLength the number of rings of hexagons apart from the center one in the board.
   */
  public HexagonBoard(int sideLength) {
    // Throw an exception if the side length is less than 3 because the board will be too small
    // to play a game.
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    }
    this.boardPositions = new HashMap<>();
    this.sideLength = sideLength;
  }

  @Override
  public void newCellOwner(ICell cell, Optional<TokenColor> color) {
    // Throws an exception if the cell is null.
    checkCellNotNull(cell);
    checkCellInBounds(cell);
    this.boardPositions.put(cell, color);
  }

  // Throws an exception if the cell is null.
  private void checkCellNotNull(ICell cell) {
    if (cell == null) {
      throw new IllegalArgumentException("Null cell passed into this method.");
    }
  }

  // Throws an exception if the cell is out of bounds.
  private void checkCellInBounds(ICell cell) {
    if (Math.abs(cell.getCoordinates().get(0)) >= sideLength
            || Math.abs(cell.getCoordinates().get(1)) >= sideLength
            || Math.abs(cell.getCoordinates().get(2)) >= sideLength) {
      throw new IllegalArgumentException("Invalid coordinates for the target cell, "
              + "coordinates out of bounds");
    }
  }

  @Override
  public Optional<TokenColor> getCellOccupant(ICell hexagonCell) throws IllegalArgumentException {
    checkCellNotNull(hexagonCell);
    checkCellInBounds(hexagonCell);
    return this.boardPositions.get(hexagonCell);
  }

  @Override
  public boolean validMove(ICell cell, TokenColor colorToAdd, boolean flip) {
    checkCellNotNull(cell);
    checkCellInBounds(cell);

    if (this.boardPositions.get(cell).isPresent()) {
      throw new IllegalStateException("Cell is already occupied.");
    }

    List<ICell> cellsFlip = calculateFlippableCells(cell, colorToAdd);
    if (flip) {
      flipMechanism(cellsFlip, colorToAdd);
    }
    return !cellsFlip.isEmpty();
  }

  // helper method to calculate the cells that will be flipped
  private List<ICell> calculateFlippableCells(ICell cell, TokenColor colorToAdd) {
    List<ICell> cellsFlip = new ArrayList<>();
    int[] dq = {1, -1, 0, 0, -1, 1};
    int[] dr = {-1, 1, -1, 1, 0, 0};
    int[] ds = {0, 0, 1, -1, 1, -1};

    for (int direction = 0; direction < 6; direction++) {
      int qChange = dq[direction];
      int rChange = dr[direction];
      int sChange = ds[direction];

      checkDirectionAddCells(cell, colorToAdd, qChange, rChange, sChange, cellsFlip);
    }
    return cellsFlip;
  }

  // helper method to check the direction of the cells to be flipped
  private void checkDirectionAddCells(ICell cell, TokenColor colorToAdd,
                                      int qChange, int rChange,
                                      int sChange, List<ICell> cellsFlip) {
    boolean foundOppositeColor = false;
    List<ICell> cellsFlipTemp = new ArrayList<>();
    int targetQ = cell.getCoordinates().get(0) + qChange;
    int targetR = cell.getCoordinates().get(1) + rChange;
    int targetS = cell.getCoordinates().get(2) + sChange;
    ICell firstCell = new HexagonCell(targetQ, targetR, targetS);

    try {
      if (getCellOccupant(firstCell).isEmpty() || getCellOccupant(firstCell).
              equals(Optional.of(colorToAdd))) {
        return;
      }
    } catch (IllegalArgumentException ignored) {
      return;
    }

    // First cell -> black
    while (isInBounds(targetQ, targetR, targetS)) {
      ICell targetCell = new HexagonCell(targetQ, targetR, targetS);
      Optional<TokenColor> cellOccupant = boardPositions.get(targetCell);

      if (cellOccupant.isEmpty()) {
        break;
      } else if (!cellOccupant.equals(Optional.of(colorToAdd))) {
        // If target cell is white then add it to the list of cells to be flipped
        cellsFlipTemp.add(targetCell);
        foundOppositeColor = true;
      } else if (foundOppositeColor) {
        cellsFlip.addAll(cellsFlipTemp); // If opposite color is found add all the cells to be
        // flipped to the list of cells to be flipped
        break;
      }
      targetQ += qChange;
      targetR += rChange;
      targetS += sChange;
    }
  }

  // helper method to check if the target cell is in bounds
  private boolean isInBounds(int targetQ, int targetR, int targetS) {
    return Math.abs(targetQ) < sideLength && Math.abs(targetR)
            < sideLength && Math.abs(targetS) < sideLength;
  }

  //helper method to flip the cells
  private void flipMechanism(List<ICell> cellsToBeFlipped, TokenColor colorToAdd) {
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
      Optional<TokenColor> occupant = boardPositions.get(cell);
      List<Integer> coordinates = cell.getCoordinates();
      int x = coordinates.get(0) + sideLength; //q
      int y = coordinates.get(1) + sideLength; //r
      boardArray[y][x] = occupant.map(TokenColor::toString).orElse("-");
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
  public int getColorCount(TokenColor color) {
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
  public List<ICell> validMovesLeft(TokenColor colorToAdd) {
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
  public Map<ICell, Optional<TokenColor>> getPositionsMapCopy() {
    Map<ICell, Optional<TokenColor>> mapCopy = new HashMap<>();
    for (ICell cell : this.boardPositions.keySet()) {
      ICell addCell = new HexagonCell(cell.getCoordinates().get(0), cell.getCoordinates().get(1),
              cell.getCoordinates().get(2));
      mapCopy.put(addCell, this.boardPositions.get(cell));
    }
    return Collections.unmodifiableMap(mapCopy);
  }

  @Override
  public int getNumRings() {
    return sideLength;
  }

}
