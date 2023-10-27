package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public boolean validMove(ICell cell, Player player) {
    for (int i = 0; i < cell.getCoordinates().size(); i++) {
      if (cell.getCoordinates().get(i) < -sideLength || cell.getCoordinates().get(i) > sideLength) {
        throw new IllegalArgumentException();
      }
    }
    //todo this is where move logic will come into play
    return false;
  }

  @Override
  public String toString() {
    int rows = 2 * sideLength;
    int columns = 2 * sideLength;

    // Create a 2D array to represent the board
    String[][] boardArray = new String[rows][columns];

    // Initialize the board with "X" for empty cells
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        boardArray[i][j] = " ";
      }
    }

    // Populate the board with player symbols or "X" based on the cell state
    for (ICell cell : boardPositions.keySet()) {
      Optional<Player> occupant = boardPositions.get(cell);
      List<Integer> coordinates = cell.getCoordinates();
      int x = coordinates.get(0) + sideLength;
      int y = coordinates.get(1) + sideLength;
      boardArray[y][x] = occupant.map(player -> player.toString()).orElse("X");
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

}
