package view;

import model.HexagonReversi;
import model.IReversiModel;

public class ReversiTextView implements TextView {
  private final IReversiModel model;
  private Appendable appendable;

  public ReversiTextView(IReversiModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return this.model.toString();
  }

}
