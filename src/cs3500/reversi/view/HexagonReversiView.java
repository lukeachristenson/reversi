package cs3500.reversi.view;

import java.awt.HeadlessException;


import javax.swing.JFrame;

import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;

/**
 * A basic view for the Reversi game. This view is a JFrame that contains a ReversiPanel. Each
 * color gets their own individual view.
 */
public class HexagonReversiView extends AbstractReversiView {

  /**
   * Constructs a BasicReversiView with the given model and frameColor.
   *
   * @param model           the model to be used
   * @param frameTokenColor the color of the frame
   * @throws HeadlessException if the environment doesn't support a keyboard, display, or mouse
   */
  public HexagonReversiView(ROModel model, TokenColor frameTokenColor) throws HeadlessException {
    super(model, frameTokenColor);
    super.setPanel(new HexagonReversiPanel(model, frameTokenColor));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack(); // Resize the frame to fit the panel
    setTitle(frameTokenColor.toString() + " player view");
  }
}