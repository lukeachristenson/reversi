package strategy;

import java.util.ArrayList;
import java.util.List;

import model.Color;
import model.IBoard;
import model.ICell;
import model.ROModel;

/**
 * This class represents a strategy that chooses the corners of the board if possible.
 */
public class ChooseCorners implements Strategy {
  private final model.Color color;

  /**
   * Constructor for a ChooseCorners strategy.
   * @param color the color of the player using this strategy.
   */
  public ChooseCorners(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    IBoard board = model.createBoardCopy();
    List<ICell> choices = (filteredMoves.isEmpty()) ? board.validMovesLeft(color) : filteredMoves;
    List<ICell> retList = new ArrayList<>();
    int sideLength = model.getDimensions();

    for (ICell cell : choices) {
      // If the cell is on the outer ring of the board, check if it is a corner cell and add
      // it to the return list.
      if ((Math.abs(cell.getCoordinates().get(0)) == sideLength - 1
              || Math.abs(cell.getCoordinates().get(1)) == sideLength - 1
              || Math.abs(cell.getCoordinates().get(2)) == sideLength - 1)) {
        if (cell.getCoordinates().get(0) + cell.getCoordinates().get(1) == 0 ||
                cell.getCoordinates().get(1) + cell.getCoordinates().get(2) == 0 ||
                cell.getCoordinates().get(2) + cell.getCoordinates().get(0) == 0) {
          retList.add(cell);
        }
      }
    }
    if (retList.isEmpty()) {
      return choices;
    }
    return retList;
  }
}
