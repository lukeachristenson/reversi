package cs3500.provider.model;

import cs3500.provider.controller.ControllerFeatures;
import cs3500.provider.strategy.ReversiStrategy;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.HumanPlayer;

public class HumanPlayerAdapter extends HumanPlayer implements Player {
  private final int playerNumber;

  public HumanPlayerAdapter(TokenColor tokenColor) {
    super(tokenColor);
    this.playerNumber = (tokenColor == TokenColor.BLACK) ? 1 : 2;
  }

  @Override
  public CubicCoordinate play(ReadonlyReversiModel model) {
    return null;
  }

  @Override
  public int getPlayerNumber() {
    return this.playerNumber;
  }

  @Override
  public ReversiStrategy getPlayerStrategy() {
    return null;
  }
}
