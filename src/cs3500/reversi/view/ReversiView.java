package cs3500.reversi.view;

import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IReversiModel;

/**
 * A view for the Reversi game.
 */
public interface ReversiView {
  /**
   * Adds a feature listener to the view.
   *
   * @param features the feature listener to be added
   */
  void addFeatureListener(PlayerFeatures features);

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
   * @param tokenColor
   * @param model
   */
  void listenToMove(TokenColor tokenColor);
}
