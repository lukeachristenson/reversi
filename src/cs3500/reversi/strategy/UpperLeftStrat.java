package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;

/**
 * Represents a strategy that chooses the uppermost-leftmost move.
 */
public class UpperLeftStrat implements Strategy {
  private final TokenColor tokenColor;

  /**
   * Constructs a new UpperLeftStrat object.
   *
   * @param tokenColor the color of the player using this strategy
   */
  public UpperLeftStrat(TokenColor tokenColor) {
    this.tokenColor = tokenColor;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    if (!model.isGameOver()) {
      boolean allValidFilteredMoves =
              filteredMoves.stream().allMatch(c -> model.getValidMoves(tokenColor).contains(c))
                      && !filteredMoves.isEmpty();

      ICell returnCell = new HexagonCell(0, -model.getDimensions(), model.getDimensions());
      List<ICell> choices =
              new ArrayList<>(allValidFilteredMoves ? filteredMoves :
                      model.getValidMoves(tokenColor));

      // Sort based on (s - r)
      if (model.getCurrentColor().equals(tokenColor)) {
        choices.sort(new UpperLeftComparator());
      }
      return choices;
    }
    return new ArrayList<>();
  }

  private static class UpperLeftComparator implements Comparator<ICell> {

    @Override
    public int compare(ICell cell1, ICell cell2) {
      int sMinusR1 = cell1.getCoordinates().get(2) - cell1.getCoordinates().get(1);
      int sMinusR2 = cell2.getCoordinates().get(2) - cell2.getCoordinates().get(1);

      // Compare based on s - r value
      int result = Integer.compare(sMinusR2, sMinusR1);

      // If s - r values are the same, compare based on maximum of s
      if (result == 0) {
        int maxS1R1 = cell1.getCoordinates().get(2);
        int maxS2R2 = cell2.getCoordinates().get(2);
        result = Integer.compare(maxS2R2, maxS1R1);
      }
      return result;
    }
  }
}