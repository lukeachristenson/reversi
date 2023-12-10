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
    for (int row = 0; row < sideLength; row++) {
      for (int col = 0; col < sideLength; col++) {
        Optional<TokenColor> occupant = boardPositions.get(new SquareCell(row, col));
        sb.append(occupant.map(TokenColor::toString).orElse("-")).append(" ");
      }
      sb.append("\n");
    }
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
      if (!boardPositions.get(cell).isPresent() && validMove(cell, colorToAdd, false)) {
        validMoves.add(cell);
      }
    }
    return validMoves;
  }

  @Override
  public void newCellOwner(ICell cell, Optional<TokenColor> color) {
    checkCellNotNull(cell);
    checkCellInBounds(cell);
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
    if (row < 0 || row >= sideLength || col < 0 || col >= sideLength) {
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
    int row = coordinates.get(0);
    int col = coordinates.get(1);
    List<ICell> tempCells = new ArrayList<>();
    boolean foundOppositeColor = false;

    row += dRow;
    col += dCol;
    while (row >= 0 && row < sideLength && col >= 0 && col < sideLength) {
      SquareCell nextCell = new SquareCell(row, col);
      Optional<TokenColor> cellOccupant = boardPositions.get(nextCell);

      if (cellOccupant.isEmpty() || cellOccupant.equals(Optional.of(colorToAdd))) {
        break;
      }

      tempCells.add(nextCell);
      foundOppositeColor = true;

      if (foundOppositeColor && cellOccupant.equals(Optional.of(colorToAdd))) {
        cellsToFlip.addAll(tempCells);
        break;
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
    return sideLength;
  }
}
