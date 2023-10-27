package view;

import model.HexagonReversi;

public class ReversiTextView implements TextView{
  private final HexagonReversi model;
  private Appendable appendable;

  public ReversiTextView(HexagonReversi model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return this.model.getBoard().toString();
  }

}
