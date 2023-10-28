package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class HexagonBoard implements IBoard {
  private final HashMap<ICell, Optional<Player>> boardPositions;
  private final int sideLength;
  public HexagonBoard(int sideLength) {
    this.boardPositions = new HashMap<>();
    this.sideLength = sideLength;
  }

  @Override
  public void newCellOwner(ICell cell, Optional<Player> player) {
    this.boardPositions.put(cell, player);
  }

  @Override
  public Optional<Player> getCellState(ICell hexagonCell) throws IllegalArgumentException{
    return this.boardPositions.get(hexagonCell);
  }

  @Override
  public boolean validMove(ICell cell, Player playerToAdd) {
    int numRows = 2 * sideLength;
    int numCols = 2 * sideLength;

    // Create a 2D array to represent the board
    Optional<Player>[][] boardArray = new Optional[numRows][numCols];

    // Populate the board with player symbols or "X" based on the cell state
    for (ICell someCell : boardPositions.keySet()) {
      Optional<Player> occupant = boardPositions.get(someCell);
      List<Integer> coordinates = someCell.getCoordinates();
      int x = coordinates.get(0) + sideLength; //q
      int y = coordinates.get(1) + sideLength; //r
      boardArray[y][x] = boardPositions.get(someCell);
    }

    int row = cell.getCoordinates().get(0) + sideLength;
    int col = cell.getCoordinates().get(1) + sideLength;

    // Define direction vectors for the six directions
    int[] dr = { -1, 1, 0, -1, 1, 0 };
    int[] dc = { 0, 0, -1, -1, -1, 1 };

    for (int direction = 0; direction < 6; direction++) {
      int r = row;
      int c = col;

      // Move to the adjacent cell in the current direction
      r += dr[direction];
      c += dc[direction];

      boolean foundOppositeColor = false;

      // Check for at least one player of the opposite color adjacent to the position
      // Check for at least one player of the opposite color adjacent to the position
      while (r >= 0 && r < numRows && c >= 0 && c < numCols) {
        if (boardArray[r][c] == playerToAdd.orElse(Player.EMPTY)) {
          // Found a player of the same color after the opposite color
          if (foundOppositeColor) {
            return true; // Valid move
          }
          break;
        } else if (boardArray[r][c] == Player.EMPTY) {
          break; // Reached an empty cell, stop checking in this direction
        } else {
          // Found a player of the opposite color
          foundOppositeColor = true;
        }

        // Move to the next cell in the same direction
        r += dr[direction];
        c += dc[direction];
      }
    }

    return false; // Invalid move
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
      Optional<Player> occupant = boardPositions.get(cell);
      List<Integer> coordinates = cell.getCoordinates();
      int x = coordinates.get(0) + sideLength ; //q
      int y = coordinates.get(1) + sideLength; //r
      boardArray[y][x] = occupant.map(Player::toString).orElse("-");
    }

//    // Adjusts the spacing for every line in the board
//    for(int i = 0 ; i < rows ; i++) {
//      for(int j = i ; j > 0 ; j--){
//        boardArray[i][0] = boardArray[i][0] + " ";
//      }
//    }

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

}
