package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a square board for the game of Reversi.
 */
public class SquareBoard implements IBoard {
  private final HashMap<ICell, Optional<TokenColor>> boardPositions;
  private final int sideLength;

  public SquareBoard(int sideLength) {
    if (sideLength < 2 || sideLength % 2 != 0) {
      throw new IllegalArgumentException("Side length must be even and greater than 2");
    }
    this.boardPositions = new HashMap<>();
    this.sideLength = sideLength;
  }

  @Override
  public boolean validMove(ICell cell, TokenColor colorToAdd, boolean flip) {
    checkCellNotNull(cell);
    checkCellInBounds(cell);

    if (this.boardPositions.get(cell).isPresent()) {
      throw new IllegalStateException("Cell is already occupied.");
    }

    List<ICell> cellsToFlip = calculateFlippableCells(cell, colorToAdd);
    if (flip) {
      flipMechanism(cellsToFlip, colorToAdd);
    }
    return !cellsToFlip.isEmpty();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    // Create a 2D array to represent the board
    String[][] boardArray = new String[sideLength][sideLength];
    int add = sideLength/2;

    // Initialize the array with " " for empty cells
    for (int i = 0; i < sideLength; i++) {
      for (int j = 0; j < sideLength; j++) {
        boardArray[i][j] = "_";
      }
    }
    System.out.println(this.boardPositions.keySet().size());

    // Populate the board with player symbols or "X" based on the cell state
    for (ICell cell : boardPositions.keySet()) {
      Optional<TokenColor> occupant = boardPositions.get(cell);
      List<Integer> coordinates = cell.getCoordinates();
      int x = Integer.min(coordinates.get(0) + add, sideLength - 1); //q
      int y = Integer.min(coordinates.get(1) + add, sideLength - 1); //r
      boardArray[x][y] = occupant.map(TokenColor::toString).orElse("-");
    }


    for (int i = 0; i < sideLength; i++) {
      for (int j = 0; j < sideLength; j++) {
        sb.append(boardArray[i][j]).append(" ");
      }
      sb.append("\n");
    }

//    for (int row = -sideLength / 2; row < sideLength/2 + 1; row++) {
//      if(row == 0 ){
//        continue;
//      }
//      for (int col = -sideLength / 2; col < sideLength/2 + 1; col++) {
//        if(col == 0){
//          continue;
//        }
//        Optional<TokenColor> occupant = boardPositions.get(new SquareCell(row, col));
//        System.out.println("Coordinates: " + row + ", " + col);
//        sb.append(occupant.map(TokenColor::toString).orElse("-")).append(" ");
//      }
//      sb.append("\n");
//    }
    return sb.toString();
  }

  @Override
  public int getColorCount(TokenColor color) {
    int count = 0;
    for (Optional<TokenColor> occupant : boardPositions.values()) {
      if (occupant.isPresent() && occupant.get() == color) {
        count++;
      }
    }
    return count;
  }

  @Override
  public List<ICell> validMovesLeft(TokenColor colorToAdd) {
    List<ICell> validMoves = new ArrayList<>();
    for (ICell cell : boardPositions.keySet()) {
      if (boardPositions.get(cell).isEmpty() && validMove(cell, colorToAdd, false)) {
        validMoves.add(cell);
      }
    }
    return validMoves;
  }

  @Override
  public void newCellOwner(ICell cell, Optional<TokenColor> color) {
    checkCellNotNull(cell);
    this.checkCellInBounds(cell);
    boardPositions.put(cell, color);
  }

  private void checkCellNotNull(ICell cell) {
    if (cell == null) {
      throw new IllegalArgumentException("Null cell passed into this method.");
    }
  }

  private void checkCellInBounds(ICell cell) {
    List<Integer> coordinates = cell.getCoordinates();
    int row = coordinates.get(0);
    int col = coordinates.get(1);
    if (Math.abs(row) > sideLength|| Math.abs(col) > sideLength) {
      throw new IllegalArgumentException("Cell coordinates out of bounds");
    }
  }

  private List<ICell> calculateFlippableCells(ICell cell, TokenColor colorToAdd) {
    List<ICell> cellsToFlip = new ArrayList<>();
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    for (int[] dir : directions) {
      int dRow = dir[0];
      int dCol = dir[1];
      checkDirectionAddCells(cell, colorToAdd, dRow, dCol, cellsToFlip);
    }
    return cellsToFlip;
  }

  private void checkDirectionAddCells(ICell cell, TokenColor colorToAdd, int dRow, int dCol, List<ICell> cellsToFlip) {
    List<Integer> coordinates = cell.getCoordinates();
    int row = coordinates.get(0) + dRow;
    int col = coordinates.get(1) + dCol;
    List<ICell> tempCells = new ArrayList<>();
    boolean foundOppositeColor = false;
    boolean continueSearch = true;

    int count = 0;

    while (continueSearch && Math.abs(row) <= sideLength / 2 && Math.abs(col) <= sideLength / 2) {
      SquareCell nextCell = new SquareCell(row, col);
      Optional<TokenColor> cellOccupant = boardPositions.get(nextCell);

      if(row == 0 || col == 0){
        row += dRow;
        col += dCol;
        continue;
      }

      if (cellOccupant.isEmpty()) {
        continueSearch = false;
      } else if (cellOccupant.equals(Optional.of(colorToAdd))) {
        if (foundOppositeColor) {
          cellsToFlip.addAll(tempCells);
        }
        continueSearch = false;
      } else {
        tempCells.add(nextCell);
        foundOppositeColor = true;
      }

      row += dRow;
      col += dCol;
    }

  }


  private void flipMechanism(List<ICell> cellsToBeFlipped, TokenColor colorToAdd) {
    for (ICell cell : cellsToBeFlipped) {
      this.newCellOwner(cell, Optional.of(colorToAdd));
    }
  }

  @Override
  public Optional<TokenColor> getCellOccupant(ICell cell) {
    checkCellNotNull(cell);
    checkCellInBounds(cell);
    return boardPositions.get(cell);
  }

  @Override
  public Map<ICell, Optional<TokenColor>> getPositionsMapCopy() {
    return Collections.unmodifiableMap(new HashMap<>(boardPositions));
  }

  @Override
  public int getNumRings() {
    return sideLength/2;
  }
}
