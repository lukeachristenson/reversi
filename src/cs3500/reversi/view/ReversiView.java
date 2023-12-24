package cs3500.reversi.view;

import cs3500.reversi.controller.IPlayerFeature;
import cs3500.reversi.model.TokenColor;

/**
 * A view for the Reversi game.
 */
public interface ReversiView {
  /**
   * Adds a feature listener to the view.
   *
   * @param features the feature listener to be added
   */
  void addFeatureListener(IPlayerFeature features);

  /**
   * Displays the view.
   *
   * @param show whether to display the view
   */
  void display(boolean show);

  /**
   * Advances the view.
   */
  void advance();

  /**
   * Displays an error message.
   */
  void error(String error);

  /**
   * Emits the move color.
   * @param tokenColor the color of the token to be moved.
   */
  void listenToMove(TokenColor tokenColor);
}
