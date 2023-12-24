package cs3500.reversi.controller;

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


  /**
   * Constructs a controller for the Reversi game.
   *
   * @param model  the model to be used for the game.
   * @param view   the view for the particular player.
   * @param player the player that the controller controls.
   * @param color  the color of the player that this controller controls.
   */
  public Controller(IReversiModel model, ReversiView view, IPlayer player, TokenColor color) {
    IReversiModel model1;
    TokenColor color1;
    ReversiView view1;
    IPlayer player1;
    try {
      color1 = Objects.requireNonNull(color);
      model1 = Objects.requireNonNull(model);
      view1 = Objects.requireNonNull(view);
      player1 = Objects.requireNonNull(player);
    } catch (NullPointerException ex) {
      throw new IllegalArgumentException("Null argument(s) given to controller constructor. " +
              ex.getMessage());
    }
    IModelFeature modelFeatures = new ModelFeatures(view1, player1);
    IPlayerFeature playerFeatures = new PlayerFeatures(model1, view1, color1);
    view.addFeatureListener(playerFeatures);
    model.addListener(modelFeatures);
    player.addListener(playerFeatures);
  }
}
