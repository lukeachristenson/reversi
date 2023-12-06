package cs3500.provider.controller;

import cs3500.provider.model.CubicCoordinate;
import cs3500.reversi.controller.IModelFeature;
import cs3500.reversi.controller.ModelFeatures;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.ReversiView;

public class ModelFeaturesAdapter implements ObserverInterface {
  private final IModelFeature modelFeatures;
  private final ReversiView view;
  private final IPlayer player;

  public ModelFeaturesAdapter(ReversiView view, IPlayer player) {
    this.view = view;
    this.player = player;
    this.modelFeatures = new ModelFeatures(view, player);
  }

  @Override
  public void getNotifiedItsYourPlayersMove() {

  }
}