package cs3500.provider.model;

import java.util.HashSet;
import java.util.Set;

import cs3500.provider.controller.ControllerAdapter;
import cs3500.provider.controller.ControllerFeatures;
import cs3500.provider.strategy.ReversiStrategy;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;

public class AIPlayerAdapter implements Player {
  private final ReadonlyReversiModel model;

  private final int playerNumber;
  private final ReversiStrategy strategy;


  public AIPlayerAdapter
          (TokenColor tokenColor, ReversiStrategy strategy,
           IReversiModel model) {

    this.playerNumber = (tokenColor == TokenColor.BLACK) ? 1 : 2;
    this.model = new ModelAdapter(model);
    this.strategy = strategy;
  }

  @Override
  public CubicCoordinate play(ReadonlyReversiModel model) {
    return this.getPlayerStrategy().chooseCoord(model, this.getPlayerNumber());
  }

  @Override
  public int getPlayerNumber() {
    return this.playerNumber;
  }

  @Override
  public ReversiStrategy getPlayerStrategy() {
    return this.strategy;
  }

}
