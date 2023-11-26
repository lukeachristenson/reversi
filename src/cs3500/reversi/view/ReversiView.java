package cs3500.reversi.view;

import cs3500.reversi.model.Color;
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
  void addFeatureListener(ViewFeatures features);

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
  void error();

  /**
   * Gets the frame color of the view.
   *
   * @return the frame color of the view
   */
  Color getFrameColor();

  void listenToMove(Color color, IReversiModel model);

}
