package mockplayerview;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.Strategy;

public class MockPlayer implements IPlayer {
  private List<PlayerFeatures> listeners;
  private final StringBuilder log;

  public MockPlayer(StringBuilder log) {
    this.listeners = new ArrayList<>();
    this.log = log;
  }


  @Override
  public void addListener(PlayerFeatures listener) {
    this.log.append("Player: addListener called\n");
    this.listeners.add(listener);
  }

  @Override
  public void playMove() {

  }


  @Override
  public void listenForMove(TokenColor tokenColor) {
    this.log.append("Player: listenForMove called\n");
  }

}
