package cs3500.reversi.view;

import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;


/**
 * This class represents a panel for the Reversi game.
 */
public class HexagonReversiPanel extends AbstractPanel{
  /**
   * Constructs a ReversiPanel with the given model and frameColor.
   *
   * @param roModel         the read only model to be used.
   * @param frameTokenColor the color of the frame.
   */
  public HexagonReversiPanel(ROModel roModel, TokenColor frameTokenColor) {
    super(roModel, frameTokenColor);
  }
}
