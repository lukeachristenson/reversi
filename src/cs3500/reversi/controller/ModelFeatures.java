package cs3500.reversi.controller;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.ModelFeature;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.ReversiView;

public class ModelFeatures implements ModelFeature{
  private final IReversiModel model;
  private final ReversiView view;
  private final IPlayer player;
  private final TokenColor color;

  public ModelFeatures(IReversiModel model, ReversiView view, IPlayer player, TokenColor color) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.color = color;
  }

  public void emitMoveColor(TokenColor tokenColor) {
    this.advanceFrame();
    this.player.listenForMove(tokenColor);
    this.view.listenToMove(tokenColor);
  }

  public void advanceFrame() {
    this.view.display(true);
    this.view.advance();
  }
}
