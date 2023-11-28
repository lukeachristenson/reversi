package cs3500.reversi.controller;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.ReversiView;

/**
 * A class to represent the features of the model.
 */
public class ModelFeatures {
  private final ReversiView view;
  private final IPlayer player;

  /**
   * Constructs a ModelFeatures object.
   *
   * @param view   the view.
   * @param player the player.
   */
  public ModelFeatures(ReversiView view, IPlayer player) {
    this.view = view;
    this.player = player;
  }

  /**
   * Emits a move to both the player and the view of this feature.
   *
   * @param tokenColor the color of the token to be moved.
   */
  public void emitMoveColor(TokenColor tokenColor) {
    this.advanceFrame();
    this.player.listenForMove(tokenColor);
    this.view.listenToMove(tokenColor);
  }

  /**
   * Advances the frame of the view and displays it.
   */
  public void advanceFrame() {
    this.view.display(true);
    this.view.advance();
  }
}
