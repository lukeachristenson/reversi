package cs3500.reversi.view;

import java.awt.HeadlessException;


import javax.swing.JFrame;

import cs3500.reversi.controller.IPlayerFeature;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;

/**
 * A basic view for the Reversi game. This view is a JFrame that contains a ReversiPanel. Each
 * color gets their own individual view.
 */
public class BasicReversiView extends JFrame implements ReversiView {
  private final ReversiPanel panel;

  /**
   * Constructs a BasicReversiView with the given model and frameColor.
   *
   * @param model           the model to be used
   * @param frameTokenColor the color of the frame
   * @throws HeadlessException if the environment doesn't support a keyboard, display, or mouse
   */
  public BasicReversiView(ROModel model, TokenColor frameTokenColor) throws HeadlessException {
    if (model == null || frameTokenColor == null) {
      throw new IllegalArgumentException("Model and frameColor cannot be null");
    }
    this.panel = new ReversiPanel(model, frameTokenColor);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(panel); // Add the panel to the frame
    this.pack(); // Resize the frame to fit the panel
    setTitle(frameTokenColor.toString() + " player view");
  }

  @Override
  public void addFeatureListener(IPlayerFeature feature) {
    this.panel.addFeaturesListener(feature);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void advance() {
    this.panel.advance();
  }

  @Override
  public void error(String error) {
    this.panel.error(error);
  }

  @Override
  public void listenToMove(TokenColor tokenColor) {
    // Do nothing
  }
}
