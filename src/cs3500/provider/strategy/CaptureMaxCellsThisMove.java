package cs3500.provider.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.provider.model.CubicCoordinate;
import cs3500.provider.model.ReadonlyReversiModel;
import cs3500.provider.model.ReversiModel;


/**
 * This Reversi strategy will capture as many pieces on this turn as possible.
 */
public class CaptureMaxCellsThisMove implements ReversiStrategy {
  @Override
  public CubicCoordinate chooseCoord(ReadonlyReversiModel model, int player) {

    CubicCoordinate maxCoord = new CubicCoordinate(0, 0, 0);
    int maxScore = 0;

    List<CubicCoordinate> keysList = new ArrayList<>(model.getBoard().keySet());
    //Collections.reverse(keysList);
    //for each valid move
    //check how much the score will increase,
    // if score > maxScore, score = maxScore, cell = maxCell
    for (CubicCoordinate coord : keysList) {
      if (model.isMoveValid(coord, player)) {
        ReversiModel clonedModelToTestMove = model.clone();

        //try?
        clonedModelToTestMove.flipCell(coord, player);

        if (clonedModelToTestMove.getScore(player) > maxScore) {
          maxScore = clonedModelToTestMove.getScore(player);
          maxCoord = coord;
          //System.out.println("Max score: " + maxScore);
        }
      }
    }
    return maxCoord;
  }
}
