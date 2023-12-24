package cs3500.reversi.controller;

import cs3500.reversi.model.TokenColor;

/**
 * An interface to represent the features of the model. These are the capabilities that the
 * controller will have to affect the model.
 */
public interface IModelFeature {

  /**
   * Emits a move to both the player and the view of this feature.
   *
   * @param tokenColor the color of the token to be moved.
   */
  public void notifyPlayerMove(TokenColor tokenColor);

  /**
   * Advances the frame of the view and displays it.
   */
  public void advanceFrame();
}
