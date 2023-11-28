package cs3500.reversi.controller;

import java.awt.*;
import java.util.Objects;

import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.ReversiView;

/**
 * A controller for the Reversi game. This controller takes in a model and two views and allows
 * the user to play the game.
 */
public class Controller{
  private final IReversiModel model;
  private final TokenColor color;
  private final ReversiView view;
  private final IPlayer player;
  private final ModelFeatures modelFeatures;
  private final PlayerFeatures playerFeatures;

  /**
   * Constructs a Controller with the given model and views.
   *
   * @param model the model to be used
   * @param view  the view to be used
   */
  public Controller(IReversiModel model, ReversiView view, IPlayer player, TokenColor color) {
    this.color = Objects.requireNonNull(color);
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.player = Objects.requireNonNull(player);
    this.modelFeatures = new ModelFeatures(this.model, this.view, this.player, this.color);
    this.playerFeatures = new PlayerFeatures(this.model, this.view, this.player, this.color);
    view.addFeatureListener(this.playerFeatures);
    model.addListener(this.modelFeatures);
    player.addListener(this.playerFeatures);
  }
}
