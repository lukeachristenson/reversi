package cs3500.provider.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.provider.model.CubicCoordinate;
import cs3500.provider.model.ReadonlyReversiModel;


/**
 * This strategy randomly chooses a valid move to make.
 */
public class RandomValidMoveStrategy implements ReversiStrategy {
  @Override
  public CubicCoordinate chooseCoord(ReadonlyReversiModel model, int player) {
    List<CubicCoordinate> keysAsArray = new ArrayList<CubicCoordinate>(model.getBoard().keySet());

    Random r = new Random();
    CubicCoordinate randomCoord = keysAsArray.get(r.nextInt(keysAsArray.size()));
    boolean validMoveExists = false;
    //if no valid moves
    for (CubicCoordinate coord : keysAsArray) {
      if (model.isMoveValid(coord, player)) {
        validMoveExists = true;
      }
    }

    while (!model.isMoveValid(randomCoord, player) && validMoveExists) {
      r = new Random();
      randomCoord = keysAsArray.get(r.nextInt(keysAsArray.size()));
    }

    return randomCoord;
  }
}
