package cs3500.reversi.view;

import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;

public class SquareReversiView extends AbstractReversiView{
  public SquareReversiView(ROModel model, TokenColor frameTokenColor) {
    super(model, frameTokenColor);
    this.setPanel(new AbstractPanel(model, frameTokenColor));
  }
}
