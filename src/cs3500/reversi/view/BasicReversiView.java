package cs3500.reversi.view;

import java.awt.HeadlessException;

import javax.swing.*;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.ROModel;

/**
 * A basic view for the Reversi game. This view is a JFrame that contains a ReversiPanel. Each
 * color gets their own individual view.
 */
public class BasicReversiView extends JFrame implements ReversiView {
  private final ReversiPanel panel;
  private final Color frameColor;

  /**
   * Constructs a BasicReversiView with the given model and frameColor.
   *
   * @param model      the model to be used
   * @param frameColor the color of the frame
   * @throws HeadlessException if the environment doesn't support a keyboard, display, or mouse
   */
  public BasicReversiView(ROModel model, Color frameColor) throws HeadlessException {
    this.frameColor = frameColor;
    this.panel = new ReversiPanel(model, this.frameColor);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(panel); // Add the panel to the frame
    this.pack(); // Resize the frame to fit the panel
    setTitle(frameColor.toString() + " player view");
  }

  @Override
  public void addFeatureListener(ViewFeatures feature) {
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
  public void error() {
    this.panel.error();
  }

  @Override
  public Color getFrameColor() {
    return frameColor;
  }

  @Override
  public void listenToMove(Color color, IReversiModel model) {

  }
}
