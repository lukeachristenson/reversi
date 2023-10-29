package model;

import java.util.ArrayList;
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
  public Optional<Player> getCellState(ICell hexagonCell) throws IllegalArgumentException {
    return this.boardPositions.get(hexagonCell);
  }

  @Override
  public boolean validMove(ICell cell, Player playerToAdd) {

    // Throw an exception if the coordinates are out of bounds of the hexagonal grid.
    if(!(Math.abs(cell.getCoordinates().get(0)) < sideLength)
            || !(Math.abs(cell.getCoordinates().get(1)) < sideLength)
            || !(Math.abs(cell.getCoordinates().get(2)) < sideLength)) {
      throw new IllegalArgumentException("Invalid coordinates for the target cell, " +
              "coordinates out of bounds");
    }

    // Throw an exception if the specified cell is occupied by a non-empty player already.
    if(this.boardPositions.get(cell).isPresent()) {
      throw new IllegalStateException("Cell is already occupied.");
    }

    // Throw an exception if the sum of coordinates is not 0.
    // ************** Note: Handled in the constructor of HexagonCell. **************

    // Define direction vectors for the six possible directions when placing a piece
    // (1, -1, 0) -> Up 1, Right 1
    // (-1, 1, 0) -> Down 1, Left 1
    // (0, -1, 1) -> Up 1, Left 1
    // (0, 1, -1) -> Down 1, Right 1
    // (-1, 0, 1) -> Left 1
    // (1, 0, -1) -> Right 1
    // difference in row direction
    int[] dq = {1, -1, 0, 0, -1, 1};
    // difference in column direction
    int[] dr = {-1, 1, -1, 1, 0, 0};
    int[] ds = {0, 0, 1, -1, 1, -1};

    int coordinateQ = cell.getCoordinates().get(0);
    int coordinateR = cell.getCoordinates().get(1);
    int coordinateS = cell.getCoordinates().get(2);


    for (int direction = 0; direction < 6; direction++) {
      int qChange = dq[direction];
      int rChange = dr[direction];
      int sChange = ds[direction];

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
        if (!boardPositions.get(targetCell).equals(Optional.of(playerToAdd))) {
          foundOppositeColor = true;
        }

        // If the cell is occupied by the same player, return true if foundOppositeColor is true.
        if(boardPositions.get(targetCell).equals(Optional.of(playerToAdd))) {
          if(foundOppositeColor) {
            return true;
          }
        }

        targetQ += qChange;
        targetR += rChange;
        targetS += sChange;
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
      int x = coordinates.get(0) + sideLength; //q
      int y = coordinates.get(1) + sideLength; //r
      boardArray[y][x] = occupant.map(Player::toString).orElse("-");
    }

    // Adjusts the spacing for every line in the board
    for(int i = 0 ; i < rows ; i++) {
      for(int j = i ; j > 0 ; j--){
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
  public void flipAllInBetween(ICell cell, Player playerToAdd) {
    // Throw an exception if the sum of coordinates is not 0.
    // ************** Note: Handled in the constructor of HexagonCell. **************

    // Define direction vectors for the six possible directions when placing a piece
    // (1, -1, 0) -> Up 1, Right 1
    // (-1, 1, 0) -> Down 1, Left 1
    // (0, -1, 1) -> Up 1, Left 1
    // (0, 1, -1) -> Down 1, Right 1
    // (-1, 0, 1) -> Left 1
    // (1, 0, -1) -> Right 1
    // difference in row direction
    int[] dq = {1, -1, 0, 0, -1, 1};
    // difference in column direction
    int[] dr = {-1, 1, -1, 1, 0, 0};
    int[] ds = {0, 0, 1, -1, 1, -1};

    int coordinateQ = cell.getCoordinates().get(0);
    int coordinateR = cell.getCoordinates().get(1);
    int coordinateS = cell.getCoordinates().get(2);


    for (int direction = 0; direction < 6; direction++) {
      int qChange = dq[direction];
      int rChange = dr[direction];
      int sChange = ds[direction];

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

        // If the cell is occupied by an empty, break
        if (boardPositions.get(targetCell).isEmpty()) {
          break;
        }

        // If the cell is occupied by the opposite player, change color.
        if (!boardPositions.get(targetCell).equals(Optional.of(playerToAdd))) {
          this.newCellOwner(targetCell, Optional.of(playerToAdd));
          foundOppositeColor = true;
        }

        // If the cell is occupied by the same player, break out of the while loop.
        if(boardPositions.get(targetCell).equals(Optional.of(playerToAdd))) {
          if(foundOppositeColor) {
            break;
          }
        }

        targetQ += qChange;
        targetR += rChange;
        targetS += sChange;
      }
    }

  }

  @Override
  public int getScore(Player player) {
    int score = 0;

    for(ICell cell : this.boardPositions.keySet()) {
      if(this.boardPositions.get(cell).equals(Optional.of(player))) {
        score++;
      }
    }
    return score;
  }

  @Override
  public List<ICell> validMovesLeft(Player playerToAdd) {


    // Check if for each empty cell:
    // If the move is a valid move, for the playerToAdd, add that cell to the return list

//    for(ICell cell : this.boardPositions.keySet()) {
//      if()
//    }



    List<ICell> ret = new ArrayList<>();


    return null;
  }

}
