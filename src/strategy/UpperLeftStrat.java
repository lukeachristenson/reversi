package strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import model.Color;
import model.HexagonCell;
import model.IBoard;
import model.ICell;
import model.ROModel;

public class UpperLeftStrat implements Strategy {
  private final Color color;

  public UpperLeftStrat(Color color) {
    this.color = color;
  }


  // TODO: Need to make this select the uppermost-leftmost move.
  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    boolean allValidFilteredMoves =
            filteredMoves.stream().allMatch(c -> model.getValidMoves(color).contains(c))
                    && !filteredMoves.isEmpty();

    ICell returnCell = new HexagonCell(0, -model.getDimensions(), model.getDimensions());
    List<ICell> choices = new ArrayList<>(allValidFilteredMoves ? filteredMoves : model.getValidMoves(color));

    // Sort based on (s - r)
    if (model.getCurrentColor().equals(color)) {
      choices.sort(new UpperLeftComparator());
    }

    return choices;
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