package cs3500.reversi.controller;

import java.awt.*;
import java.util.Objects;

import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.ReversiView;

/**
 * A controller for the Reversi game. This controller takes in a model and a view and allows the
 * user to play the game. The controller also allows the user to quit the game at any time. It
 * delegates the responsibility of handling the user's input to two other package private classes,
 * ModelFeatures and PlayerFeatures to handle AI and Human player inputs.
 */
public class Controller {
  private final IReversiModel model;
  private final TokenColor color;
  private final ReversiView view;
  private final IPlayer player;
  private final ModelFeatures modelFeatures;
  private final PlayerFeatures playerFeatures;


  /**
   * Constructs a controller for the Reversi game.
   *
   * @param model  the model to be used for the game.
   * @param view   the view for the particular player.
   * @param player the player that the controller controls.
   * @param color  the color of the player that this controller controls.
   */
  public Controller(IReversiModel model, ReversiView view, IPlayer player, TokenColor color) {
    this.color = Objects.requireNonNull(color);
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.player = Objects.requireNonNull(player);
    this.modelFeatures = new ModelFeatures(this.view, this.player);
    this.playerFeatures = new PlayerFeatures(this.model, this.view, this.color);
    view.addFeatureListener(this.playerFeatures);
    model.addListener(this.modelFeatures);
    player.addListener(this.playerFeatures);
  }
}
