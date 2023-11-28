package mockplayerview;

import java.awt.*;

import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.view.ReversiView;

public class MockView implements ReversiView {
  private final StringBuilder log;

  /**
   * Constructs a BasicReversiView with the given model and frameColor.
   *
   * @param model      the model to be used
   * @param frameTokenColor the color of the frame
   * @throws HeadlessException if the environment doesn't support a keyboard, display, or mouse
   */
  public MockView(StringBuilder log) throws HeadlessException {
    this.log = log;
  }

  @Override
  public void addFeatureListener(PlayerFeatures feature) {
    this.log.append("View: addFeatureListener called\n");
  }

  @Override
  public void display(boolean show) {
    this.log.append("View: display called with show: " + show + "\n");
  }

  @Override
  public void advance() {
    this.log.append("View: advance called\n");
  }

  @Override
  public void error(String error) {
    this.log.append("View: error called\n");
  }

  @Override
  public void listenToMove(TokenColor tokenColor) {
    this.log.append("View: listenToMove called with color: " + tokenColor.toString() + "\n");
  }
}
